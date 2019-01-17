package com.huboot.commons.filter;


import com.huboot.commons.component.auth.JwtClaims;
import com.huboot.commons.component.auth.JwtTokenComponent;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * token的校验
 * 该类继承自OncePerRequestFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 *
 * @author wangjianchun on 2018/08/27.
 */
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenComponent jwtTokenComponent;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private List<String> ignoreList = new ArrayList<>();

    public JWTAuthenticationFilter(JwtTokenComponent jwtTokenComponent) {
        this.jwtTokenComponent = jwtTokenComponent;
        ignoreList.add("/task_client/**");
    }

    public void addIgnorePattern(String pattern) {
        ignoreList.add(pattern);
    }

    public void addIgnorePattern(List<String> patternList) {
        ignoreList.addAll(patternList);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if(ignore(request)) {
            chain.doFilter(request, response);
        } else {
            String authorization = request.getHeader(jwtTokenComponent.getJwtProperties().HTTP_HEADER);
            if (authorization == null || !authorization.startsWith(jwtTokenComponent.getJwtProperties().TOKEN_HEAD)) {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
            } else {
                RequestInfo.put(RequestInfo.ACCESS_TOKEN, authorization);
                authorization = authorization.replace(jwtTokenComponent.getJwtProperties().TOKEN_HEAD, "");
                JwtClaims jwtClaims = jwtTokenComponent.decode(authorization);
                RequestInfo.put(RequestInfo.JWT_USER, jwtClaims.getUser());
                chain.doFilter(request, response);
            }
        }
    }

    private boolean ignore(HttpServletRequest request) {
        for(String pattern : ignoreList) {
            if(antPathMatcher.match(pattern, request.getServletPath())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        Assert.notNull(this.jwtTokenComponent,
                "An jwtTokenComponent is required");
    }

}
