package com.huboot.business.base_model.login.sso.client.secruity.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.huboot.business.base_model.login.sso.client.component.JwtTokenComponent;
import com.huboot.business.base_model.login.sso.client.component.SpringComponent;
import com.huboot.business.base_model.login.sso.client.dto.login.RequestHeader;
import com.huboot.business.base_model.login.sso.client.interceptor.RequestInfo;
import com.huboot.business.base_model.login.sso.client.utils.RequestUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 用户权限校验filter
 **/
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    public static final String REQ_ATTR_JUER = "juser";

    public static final String REQ_ATTR_CLAIMS = "claims";

    @Autowired
    private JwtTokenComponent jwtTokenComponent;

    @Autowired
    private SpringComponent springComponent;

    @Autowired
    private PathMatchingResourcePatternResolver resolver;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${sso.fromSys}")
    private String fromSys;

    @Value("${jwt.expiration}")
    private Long expiration;

//    @Autowired
//    private JwtConfig jwtConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (logger.isDebugEnabled()) logger.debug("收到请求:{}", request.getRequestURI());
        if(request.getRequestURI().contains("swagger") && !springComponent.getApplicationContext().containsBean("swaggerConfig")){
            logger.warn("没有swagger相关配置");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print("没有swagger相关配置");
            return;
        }
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            chain.doFilter(request, response);
            return;
        }
//        if(jwtConfig.getPermit().stream().map(s -> s.getUrl()).anyMatch(t -> resolver.getPathMatcher().match(t,request.getRequestURI()))) {
//            chain.doFilter(request, response);
//            return ;
//        }
        try {
            String authHeader = request.getHeader(tokenHeader);
            if (authHeader != null && authHeader.startsWith(tokenHead)) {
                final String authToken = authHeader.substring(tokenHead.length()); // The part after "Bearer "
                if (logger.isDebugEnabled()) logger.debug("获取到到头:{}", authToken);
                //parse token
                Claims claims = jwtTokenComponent.parseToken(authToken);
                if (claims == null) throw new AccessDeniedException("签名有误或者签名已过期,请重新登录");
                //check shop
                if (!StringUtils.isEmpty(claims.get(JwtTokenComponent.CLAIM_KEY_SHOP_UID)) && !StringUtils.isEmpty(request.getHeader(JwtTokenComponent.CLAIM_KEY_SHOP_UID)) && !request.getHeader(JwtTokenComponent.CLAIM_KEY_SHOP_UID).equals(claims.get(JwtTokenComponent.CLAIM_KEY_SHOP_UID)))
                    throw new AccessDeniedException("非法数据访问,请重新登陆");
                //validateion sign
                RequestHeader requestHeader = RequestUtil.getJwtSignRequestHeader(request);
                if (!jwtTokenComponent.validateSign(requestHeader, claims))
                    throw new AccessDeniedException("签名错误,请重新登陆");
                //过期校验
                if (jwtTokenComponent.isTokenExpired(claims)) throw new AccessDeniedException("本次登陆已失效，请重新登陆");
                //刷新验证信息
                refreshAuth(request, response, claims);
                //check sys
                String sys = jwtTokenComponent.getCurrentSys(claims);
                if (!fromSys.equals(sys)) {//跨系统处理
                    claims = jwtTokenComponent.overWriteBidAndRoles(claims);
                    //TODO refreshAuth
                    String token = jwtTokenComponent.refreshAuth(claims);
                    response.setHeader(tokenHeader, tokenHead + token);
                }
                //write auth content
                UserDetails userDetails = jwtTokenComponent.getUserDetails(claims);
                if (userDetails != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    //authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    authentication.setDetails(userDetails);
                    if (logger.isDebugEnabled())
                        logger.debug("authenticated user " + userDetails.getUsername() + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                //set attribute
                request.setAttribute(REQ_ATTR_JUER, userDetails);
                request.setAttribute(REQ_ATTR_CLAIMS, claims);
                RequestInfo.put(REQ_ATTR_CLAIMS, claims);
            }
        } catch (AccessDeniedException e) {
            logger.error("JWT拦截异常:{}", e.getMessage());
        }
        chain.doFilter(request, response);
    }


    /**
     * 过期时间校验
     *
     * @throws UnsupportedEncodingException
     * @throws JsonProcessingException
     **/
    private void refreshAuth(HttpServletRequest request, HttpServletResponse response, Claims claims) throws UnsupportedEncodingException, JsonProcessingException {
        Date exp = jwtTokenComponent.getExpirationDateFromToken(claims);
        Instant instant = exp.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime expDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, expDateTime);
        if (duration.toMinutes() > 0 & duration.toMinutes() < (expiration / 2)) {//时间过一半
            //TODO refreshAuth
            String token = jwtTokenComponent.refreshAuth(claims);
            response.setHeader(tokenHeader, tokenHead + token);
        }
    }


}
