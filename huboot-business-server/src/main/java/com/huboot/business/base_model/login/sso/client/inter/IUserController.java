package com.huboot.business.base_model.login.sso.client.inter;


import com.huboot.business.base_model.login.sso.client.dto.BaseUser;
import com.huboot.business.base_model.login.sso.client.dto.change_user_status.ChangeUserStatusDTO;
import com.huboot.business.base_model.login.sso.client.dto.find_pwd.FindPwdDTO;
import com.huboot.business.base_model.login.sso.client.dto.login.ClientInfo;
import com.huboot.business.base_model.login.sso.client.dto.login.LoginDTO;
import com.huboot.business.base_model.login.sso.client.dto.logout.LogoutDTO;
import com.huboot.business.base_model.login.sso.client.dto.reset_pwd.ResetPwdDTO;
import com.huboot.business.base_model.login.sso.client.dto.sync_user.SyncUserReqDTO;
import com.huboot.business.base_model.login.sso.client.exception.SsoException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


/**
 * 说明:sso controller
 * **/
public interface IUserController {
	
	public static final String SSO_REGIST = "/base_model/sso/regist";//注册
	
	public static final String SSO_LOGIN = "/base_model/sso/login";//登录
	
	public static final String SSO_LOGINOUT = "/base_model/sso/logout/{gid}";//退出登录
	
	public static final String SSO_FIND_PWD = "/base_model/sso/pwd/find/{account}";//找回密码

	public static final String SSO_RESET_PWD = "/base_model/sso/pwd/reset/{gid}";//重置密码
	
	public static final String SSO_CHANGE_USER = "/base_model/sso/account_s/{account}";//改变用状态
	
	public static final String SSO_GET_LOGIN_INFO_BY_GID = "/base_model/sso/login_info/{gid}";//获取用户登录信息
	
	public static final String SSO_GET_ACCOUNT_INFO_BY_GID = "/base_model/sso/account_g/{gid}";////获取用户基本信息
	
	public static final String SSO_GET_ACCOUNT_INFO_BY_ACCOUNT = "/base_model/sso/account_a/{account}";//获取用户基本信息

	public static final String SSO_SYNC_USERINFO = "/base_model/sso/sync_userinfo";//同步用户信息

	public static final String SSO_SYNC_GID_USERINFO = "/base_model/sso/sync_gid_userinfo";//同步用户gid信息
	
	/**
	 * 说明:用户注冊
	 * @param loginDTO
	 * @return JwtUser
	 * **/
	@RequestMapping(value = SSO_REGIST,method = RequestMethod.POST)
	public BaseUser regist(@RequestBody LoginDTO loginDTO) throws SsoException,ClassNotFoundException;
	
	/**
	 * 说明:用户登陆
	 * @param loginDTO
	 * @return JwtUser
	 * **/
	@RequestMapping(value = SSO_LOGIN,method = RequestMethod.POST)
	public BaseUser login(@RequestBody LoginDTO loginDTO) throws SsoException,ClassNotFoundException;
	
	/**
	 * 说明:退出登陆
	 * @param gid
	 * @return JwtUser
	 * **/
	@RequestMapping(value = SSO_LOGINOUT,method = RequestMethod.POST)
	public void loginOut(@RequestBody @Validated LogoutDTO logoutDto, @PathVariable("gid") String gid) throws SsoException;
	
	
	/**
	 * 说明:找回密码
	 * @param findPwdDto
	 * @param account
	 * @return JwtUser
	 * **/
	@RequestMapping(value = SSO_FIND_PWD,method = RequestMethod.POST)
	public BaseUser findPwd(@RequestBody FindPwdDTO findPwdDto, @PathVariable String account) throws SsoException;
	
	/**
	 * 说明:忘记密码
	 * @param resetPwdDto
	 * @param gid
	 * @return JwtUser
	 * **/
	@RequestMapping(value = SSO_RESET_PWD,method = RequestMethod.POST)
	public BaseUser resetPwd(@RequestBody ResetPwdDTO resetPwdDto, @PathVariable String gid) throws SsoException,ClassNotFoundException;
	
	/**
	 * 说明:更改用户状态
	 * @param account
	 * @param changeUserStatusDto
	 * @return JwtUser
	 * **/
	@RequestMapping(value = SSO_CHANGE_USER,method = RequestMethod.POST)
	public BaseUser changeUserStatus(@PathVariable("account") String account, @Validated @RequestBody ChangeUserStatusDTO changeUserStatusDto);
	/**
	 * 说明:获取用户登陆信息
	 * @param gid
	 * @return JwtUser
	 * **/
	@RequestMapping(value = SSO_GET_LOGIN_INFO_BY_GID,method = RequestMethod.GET)
	public List<ClientInfo> getLoginInfoByGid(@PathVariable("gid") String gid) throws SsoException;
	
	/**
	 * 说明:获取用户账户信息
	 * @param gid
	 * @return JwtUser
	 * **/
	@RequestMapping(value = SSO_GET_ACCOUNT_INFO_BY_GID,method = RequestMethod.GET)
	public BaseUser getAccountInfoByGid(@PathVariable("gid") String gid) throws SsoException;
	
	/**
	 * 说明:获取用户账户信息
	 * @param account
	 * @return JwtUser
	 * **/
	@RequestMapping(value = SSO_GET_ACCOUNT_INFO_BY_ACCOUNT,method = RequestMethod.GET)
	public BaseUser getAccountInfoByAccount(@PathVariable("account") String account) throws SsoException;

	/**
	 * 说明:同步用户信息
	 * @param syncUserReqDTO
	 * @return JwtUser
	 * **/
	@RequestMapping(value = SSO_SYNC_USERINFO,method = RequestMethod.POST)
	public BaseUser syncUserInfo(@RequestBody SyncUserReqDTO syncUserReqDTO) throws ClassNotFoundException;

	/**
	 * 说明:同步用户信息
	 * @param users
	 * @return JwtUser
	 * **/
	@RequestMapping(value = SSO_SYNC_GID_USERINFO,method = RequestMethod.POST)
	public void syncUserInfo2Mongo(@RequestBody List<BaseUser> users) throws ClassNotFoundException;
}