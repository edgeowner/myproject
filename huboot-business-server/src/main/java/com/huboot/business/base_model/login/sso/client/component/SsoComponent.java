package com.huboot.business.base_model.login.sso.client.component;

import com.huboot.business.base_model.login.sso.client.exception.SsoException;

import com.huboot.business.base_model.login.sso.client.interceptor.RequestInfo;
import com.huboot.business.base_model.login.sso.client.secruity.filter.JwtAuthenticationTokenFilter;
import com.huboot.business.base_model.login.sso.client.secruity.user.ApiUser;
import com.huboot.business.base_model.login.sso.client.secruity.user.JwtUser;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;


@Component
public class SsoComponent {

	private static final Logger logger =  LoggerFactory.getLogger(SsoComponent.class);
	
	public PasswordEncoder getPasswordEncoder(String encrypeClassz) {
		if(StringUtils.isEmpty(encrypeClassz)){
			logger.error("入参encrypeClassz为空");
			throw new SsoException("PasswordEncoder不能为空");
		}
		try {
			@SuppressWarnings("unchecked")
			Class<PasswordEncoder> classz = (Class<PasswordEncoder>) Class.forName(encrypeClassz);
			PasswordEncoder encoder = classz.newInstance();
			return encoder;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
			// TODO Auto-generated catch block
			logger.error("类路:" + encrypeClassz + "径不存在或转换出错，请检查");
			throw new SsoException(ex);
		}
	}

	/**
	 * 获取当前登录用户信息
	 * @return
	 */
	public JwtUser getCurrentLoginUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object object = authentication.getDetails();
		if(object ==  null){
			if(logger.isDebugEnabled()) logger.debug("当前用户没有登录或登录信息已失效");
			return null;
		}
		if(object instanceof JwtUser){
			return (JwtUser) object;
		}
		return null;
	}

	/**
	 *
	 * 获取当前Api请求用户信息
	 * @return
	 */
	public ApiUser getCurrentSignUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object object = authentication.getDetails();
		if(object ==  null){
			if(logger.isDebugEnabled()) logger.debug("当前用户没有登录或登录信息已失效");
			return null;
		}
		if(object instanceof ApiUser){
			return (ApiUser) object;
		}
		return null;
	}

	/**
	 * 获取当前登录用户信息的业务系统id
	 * @return
	 */
	public static String getCurrentBid(){
		Object o = RequestInfo.get(JwtAuthenticationTokenFilter.REQ_ATTR_CLAIMS);
		if(o == null) {
			if(logger.isDebugEnabled()) logger.debug("当前用户没有登录或登录信息已失效");
			return null;
		}
		Map<String,Object> map = ((Claims) o);
		if(map.containsKey(JwtTokenComponent.CLAIM_KEY_BID)){
			return (String) map.get(JwtTokenComponent.CLAIM_KEY_BID);
		}
		logger.warn("当前用户没有bid");
		return null;
	}



}
