package com.huboot.business.base_model.login.sso.client.service;


import com.huboot.business.base_model.login.sso.client.component.SpringComponent;
import com.huboot.business.base_model.login.sso.client.dto.BaseUser;
import com.huboot.business.base_model.login.sso.client.dto.find_pwd.FindPwdDTO;
import com.huboot.business.base_model.login.sso.client.dto.login.ClientInfo;
import com.huboot.business.base_model.login.sso.client.dto.login.LoginDTO;
import com.huboot.business.base_model.login.sso.client.dto.logout.LogoutDTO;
import com.huboot.business.base_model.login.sso.client.dto.reset_pwd.ResetPwdDTO;
import com.huboot.business.base_model.login.sso.client.dto.sync_user.SyncUserReqDTO;
import com.huboot.business.base_model.login.sso.client.exception.SsoException;
import com.huboot.business.base_model.login.sso.client.inter.IUserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class JwtUserService {
	
	private static final Logger logger =  LoggerFactory.getLogger(JwtUserService.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SpringComponent springComponent;
	
	@Value("${sso.fromSys:sso_client_default}")
	private String fromSys;
	
	@Value("${sso.passwordEncoderClass:org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder}")
	private String encoderClass;
	
	@Value("${sso.server.host}")
	private String host;
	
	
	/**
	 * 说明:用户注冊
	 * @param loginDTO
	 * @return JwtUser
	 * @throws ClassNotFoundException
	 * **/
	public BaseUser regist(LoginDTO loginDTO) throws ClassNotFoundException{
		if(logger.isDebugEnabled()) logger.debug(loginDTO.toString());
		if(StringUtils.isEmpty(loginDTO.getFromSys())){
			loginDTO.setFromSys(fromSys);
		}
		loginDTO.setPasswordEncoder(encoderClass);
		String pass = loginDTO.getPassword();
		PasswordEncoder encode = (PasswordEncoder) springComponent.getBean(Class.forName(encoderClass));
		String hashed  = encode.encode(pass);
		loginDTO.setPassword(hashed);
		logger.info("注册请求内容:{}",loginDTO.toString());
		return restTemplate.postForObject(host + IUserController.SSO_REGIST, loginDTO, BaseUser.class);
	}
	
	
	/**
	 * 说明:用户登陆
	 * @param loginDTO
	 * @return JwtUser
	 * **/
	public BaseUser login(LoginDTO loginDTO){
		if(StringUtils.isEmpty(loginDTO.getFromSys()))loginDTO.setFromSys(fromSys);
		return restTemplate.postForObject(host + IUserController.SSO_LOGIN, loginDTO, BaseUser.class);
	}
	
	
	/**
	 * 说明:退出登陆
	 * @param logoutDto
	 * @return JwtUser
	 * **/
	public BaseUser loginOut(String gid,LogoutDTO logoutDto){
		return restTemplate.postForObject(host + IUserController.SSO_LOGINOUT, logoutDto, BaseUser.class,gid);
	}
	
	/**
	 * 说明:找回密码
	 * @param findPwdDto
	 * @param account
	 * @return JwtUser
	 * **/
	public BaseUser findPwd(FindPwdDTO findPwdDto, String account) throws ClassNotFoundException{
		String pass = findPwdDto.getNewPassword();
		PasswordEncoder encode = (PasswordEncoder) springComponent.getBean(Class.forName(encoderClass));
		String hashed  = encode.encode(pass);
		findPwdDto.setNewPassword(hashed);
		findPwdDto.setFromSys(fromSys);
		findPwdDto.setPasswordEncoder(encoderClass);
		return restTemplate.postForObject(host + IUserController.SSO_FIND_PWD, findPwdDto, BaseUser.class,account);
	}
	
	/**
	 * 说明:忘记密码
	 * @param resetPwdDto
	 * @param gid
	 * @return JwtUser
	 * **/
	public BaseUser resetPwd(ResetPwdDTO resetPwdDto, String gid){
		resetPwdDto.setPasswordEncoder(encoderClass);
		return restTemplate.postForObject(host + IUserController.SSO_RESET_PWD, resetPwdDto, BaseUser.class,gid);
	}
	
	/**
	 * 说明:获取用户登陆信息
	 * @param gid
	 * @return JwtUser
	 * **/
	public List<ClientInfo> getLoginInfoByGid(String gid){
		ParameterizedTypeReference<List<ClientInfo>> typeRef = new ParameterizedTypeReference<List<ClientInfo>>(){};
		ResponseEntity<List<ClientInfo>> responseEntity = restTemplate.exchange(host + IUserController.SSO_GET_LOGIN_INFO_BY_GID, HttpMethod.GET, null, typeRef,gid);
		if(!responseEntity.getStatusCode().equals(HttpStatus.OK)){
			logger.error("远程服务调用出差",responseEntity);
			throw new SsoException("没有找到用户的登陆信息");
		}
		return responseEntity.getBody();
	}
	
	/**
	 * 说明:获取用户账户信息(此方法不会走缓存,会直接查询DB,建议使用getAccountInfoByAccount()方法)
	 * @param gid
	 * @return JwtUser
	 * **/
	public BaseUser getAccountInfoByGid(String gid){
		return restTemplate.getForObject(host + IUserController.SSO_GET_ACCOUNT_INFO_BY_GID, BaseUser.class, gid);
	}
	
	/**
	 * 说明:获取用户账户信息
	 * @param account
	 * @return JwtUser
	 * **/
	public BaseUser getAccountInfoByAccount(String account){
		return restTemplate.getForObject(host + IUserController.SSO_GET_ACCOUNT_INFO_BY_ACCOUNT, BaseUser.class,account);
	}

	/**
	 * 说明:同步用户信息
	 * @param syncUserReqDTO
	 * @return JwtUser
	 * **/
	public BaseUser syncUserInfo(SyncUserReqDTO syncUserReqDTO){
		return restTemplate.postForObject(host + IUserController.SSO_SYNC_USERINFO, syncUserReqDTO, BaseUser.class);
	}

	/**
	 * 说明:同步用户gid到mongoDB
	 * @param list
	 * @return JwtUser
	 * **/
	public void syncUserInfo(List<BaseUser> list){
		restTemplate.postForObject(host + IUserController.SSO_SYNC_GID_USERINFO, list ,Void.class);
	}



}
