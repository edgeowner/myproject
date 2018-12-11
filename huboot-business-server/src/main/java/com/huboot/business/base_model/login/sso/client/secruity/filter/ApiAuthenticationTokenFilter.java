package com.huboot.business.base_model.login.sso.client.secruity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.huboot.business.base_model.login.sso.client.config.ApiAuthenticationConfig;
import com.huboot.business.base_model.login.sso.client.dto.api_security.ApiSecurityDTO;
import com.huboot.business.base_model.login.sso.client.exception.SsoException;
import com.huboot.business.base_model.login.sso.client.exception.ValueAbsentException;
import com.huboot.business.base_model.login.sso.client.secruity.UserFactory;
import com.huboot.business.base_model.login.sso.client.support.BodyReaderHttpServletRequestWrapper;
import com.huboot.business.base_model.login.sso.client.utils.WebUtil;
import com.huboot.business.base_model.login.sso.client.utils.encrypt.AESUtil;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * api权限校验filter
 * **/
@Component
public class ApiAuthenticationTokenFilter extends OncePerRequestFilter {
	
	private static final Logger logger =  LoggerFactory.getLogger(ApiAuthenticationTokenFilter.class);
	
	public static final String API_KEY_SIGN = "sign";
	
	public static final String API_KEY_SYS = "sys";
	
	public static final String API_MAP_SIZE ="gSize";
	
	public static final String API_MAP_TYPE ="gType";
	
	public static final String API_MAP_TIME ="gTime";
	
	public static final String API_REQ_TYPE_VOID = "Void";

	public static final String API_REQ_TYPE_COLLECTION = "Collection";
	
	public static final String API_REQ_TYPE_MAP = "Map";
	
	public static final String API_REQ_TYPE_NUMBER = "Number";
	
	public static final String API_REQ_TYPE_STRING = "String";
	
	@Autowired
	private ApiAuthenticationConfig authenticationConfig;
	
	@Autowired
	@Qualifier("objectMapper")
    private ObjectMapper mapper;
	
	@Autowired
	private PathMatchingResourcePatternResolver  resolver;
	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//req url: abc.com/?sign=aaa&sys=bbb
		if(!resolver.getPathMatcher().match(authenticationConfig.getPath(), request.getRequestURI())) {
			chain.doFilter(request, response);
			return ;
		}
		String queryStr = request.getQueryString();
		if(Objects.isNull(queryStr) || "".equals(queryStr)) throw new SsoException("签名不能为空");
		//处理QueryString
		List<Tuple2<String,String>> tuples = Arrays.asList(queryStr.split("&")).stream().map(s -> {
			String[] subStr = s.split("=");
			return Tuple.of(subStr[0],subStr[1]);
		}).collect(Collectors.toList());
		//分别获取sign 和  sys
		String sys = tuples.stream().filter(s -> s._1.equals(API_KEY_SYS)).findFirst().orElseThrow(ValueAbsentException::new)._2;
		String sign = tuples.stream().filter(s -> s._1.equals(API_KEY_SIGN)).findFirst().orElseThrow(ValueAbsentException::new)._2;
		if(sign.contains("%")) sign = URLDecoder.decode(sign, StandardCharsets.UTF_8.name());
		if(logger.isDebugEnabled()) logger.debug("获取到的请求sign:{},sys:{}",sys,sign);
		Optional<ApiSecurityDTO> optional = authenticationConfig.getAuthentication().stream().filter(s -> s.getSysName().equals(sys)).findFirst();
		if(!optional.isPresent()) throw new SsoException("你没有权限访问该接口,请向管理员申请");
		if(logger.isDebugEnabled()) logger.debug("sys:{}在配置文件中存在",sys);
		//解码sign
		String signDecode = AESUtil.decrypt(Base64.getDecoder().decode(sign), AESUtil.getRawKey(optional.get().getKey().getBytes()));
		if(logger.isDebugEnabled()) logger.debug("sign解码后为:{}",signDecode);
		//把sign转为一个map
		Map<String,String> signMap = Arrays.asList(signDecode.split("&")).stream().map(s -> {
			String[] subStr = s.split("=");
			return Tuple.of(subStr[0],subStr[1]);
		}).collect(Collectors.toMap(Tuple2::_1, t -> t._2));
		//检查时间是否超时
		LocalDateTime signTime = LocalDateTime.parse(URLDecoder.decode(signMap.get(API_MAP_TIME), StandardCharsets.UTF_8.name()), WebUtil.DATE_YYYY_MM_DD_HH_MM_SS);
		if(LocalDateTime.now().isAfter(signTime.plusSeconds(10000))) throw new SsoException("请求时间过长,请重试");
		//get
		if(HttpMethod.GET.matches(request.getMethod())) {
			//这里其实是string Map<String, Object> -->Map<String, String>
			Map<String, Object> reqMap = tuples.stream().filter(s -> !s._1.equals(API_KEY_SYS)).filter(s -> !s._1.equals(API_KEY_SIGN)).collect(Collectors.toMap(Tuple2::_1, t -> t._2));
			//get请求时，无业务参数,直接授权通过
			if(reqMap == null || reqMap.keySet().size() == 0) {
				grantAuthorization(request,optional.get());
				chain.doFilter(request, response);
				return ;
			};
			//check map
			compareMap(signMap,reqMap);
			grantAuthorization(request,optional.get());
			chain.doFilter(request, response);
			return ;
		}
		//wrapper request and read request body
		ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
		String reqBodyStr = WebUtil.getBodyString(requestWrapper);
		if(logger.isDebugEnabled()) logger.debug("读取请求body为{}",reqBodyStr);
		//请求api,无需任何参数时------->直接过
		if((reqBodyStr == null || "".equals(reqBodyStr)) && signMap.get(API_MAP_TYPE).equals(API_REQ_TYPE_VOID)) {
			grantAuthorization(request,optional.get());
			chain.doFilter(requestWrapper, response);
			return ;
		}

		//有参数时，需要对不同的content-type做适配 
		//post->application/json;charset=UTF-8
		//if(HttpMethod.POST.matches(request.getMethod()) && request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)) {
			if(reqBodyStr.startsWith("[") && reqBodyStr.endsWith("]")) {
				List<?> reqList = mapper.readValue(reqBodyStr,List.class);
				if(reqList.size() != Integer.valueOf(signMap.get(API_MAP_SIZE)).intValue() || !API_REQ_TYPE_COLLECTION.equals(signMap.get(API_MAP_TYPE))) throw new SsoException("签名不合法");
				grantAuthorization(request,optional.get());
			}else {
				@SuppressWarnings("unchecked")
				Map<String,Object> reqMap = mapper.readValue(reqBodyStr,Map.class);
				//check map
				compareMap(signMap,reqMap);
				grantAuthorization(request,optional.get());
			}
		//}
			chain.doFilter(requestWrapper, response);
		
	}
	
	/**
	 * 说明:map对比
	 * @param signMap
	 * @param reqMap
	 * ***/
	private void compareMap(Map<String,String> signMap,Map<String,Object> reqMap) {
		//处理请求map
		if(reqMap.containsKey(API_MAP_SIZE)) reqMap.remove(API_MAP_SIZE);
		if(reqMap.containsKey(API_MAP_TYPE)) reqMap.remove(API_MAP_TYPE);
		if(reqMap.containsKey(API_MAP_TIME)) reqMap.remove(API_MAP_TIME);
		if(signMap.containsKey(API_MAP_TIME)) signMap.remove(API_MAP_TIME);
		signMap.entrySet().forEach(s ->{
			if(!reqMap.containsKey(s.getKey())) throw new SsoException("签名数据不合法");
			String t1 = reqMap.get(s.getKey()) == null ? "" : reqMap.get(s.getKey()).toString();
			String t2 = s.getValue() == null ? "" : s.getValue();
            if (!t1.equals(t2)) throw new SsoException("签名错误");
		});
	}
	
	/**
	 * 说明:验证通过时，进行授权并do chain
	 * @param request
	 * @param apiSecurityDTO
	 * ***/
	private void grantAuthorization(HttpServletRequest request,ApiSecurityDTO apiSecurityDTO) {
		//write auth content
        UserDetails userDetails = UserFactory.create(UUID.randomUUID().toString().replace("-", ""), apiSecurityDTO.getSysName(), "", apiSecurityDTO.getRoles());
        if (userDetails !=null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        	authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        	logger.info("authenticated user " + userDetails.getUsername() + ", setting security context");
        	SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        //set attribute
        request.setAttribute(JwtAuthenticationTokenFilter.REQ_ATTR_JUER, userDetails);
	}
	
	
	
	
	
}
