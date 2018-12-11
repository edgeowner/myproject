package com.huboot.business.base_model.login.sso.client.dto.login;

import com.huboot.business.base_model.login.sso.client.dto.login.inter.ILogin;
import com.huboot.business.base_model.login.sso.client.dto.login.inter.IRegist;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class LoginDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "账号不能为空",groups = {ILogin.class,IRegist.class})
	private String account;
	
	@NotBlank(message = "密码不能为空",groups = {ILogin.class,IRegist.class})
	private String password;

	/** 登录渠道 */
	@NotNull(message = "登陆渠道不能为空",groups = {ILogin.class})
	private LoginType loginType;
	
	@NotBlank(message = "系统来源不能为空",groups = {IRegist.class})
	private String fromSys;
	
	@NotBlank(message = "加密机不能为空",groups = {IRegist.class})
	private String passwordEncoder;

	//needValidate 是否需要校验密码,如果为false时,则不会校验密码.主要是考虑到手机号码+验证码的方式登录
	private Boolean validatePwd = false;
	
	private ClientInfo clientInfo;
	
	private RequestHeader requestHeader;
	
	
	
	public LoginDTO() {
		// TODO Auto-generated constructor stub
	}

	public enum OptType{
		login,regist,logOut,findPwd,resetPwd;

		OptType() {
		}
	}
	
	public enum LoginType {
		b2c,miniapp,miniRisk;

		LoginType() {
		}
	}

	public Boolean getValidatePwd() {
		return validatePwd;
	}

	public void setValidatePwd(Boolean validatePwd) {
		this.validatePwd = validatePwd;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	public ClientInfo getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}

	public RequestHeader getRequestHeader() {
		return requestHeader;
	}

	public void setRequestHeader(RequestHeader requestHeader) {
		this.requestHeader = requestHeader;
	}

	public String getFromSys() {
		return fromSys;
	}

	public void setFromSys(String fromSys) {
		this.fromSys = fromSys;
	}

	public String getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(String passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public LoginDTO(String account, String password, LoginType loginType, String fromSys,
			String passwordEncoder, ClientInfo clientInfo, RequestHeader requestHeader) {
		super();
		this.account = account;
		this.password = password;
		this.loginType = loginType;
		this.fromSys = fromSys;
		this.passwordEncoder = passwordEncoder;
		this.clientInfo = clientInfo;
		this.requestHeader = requestHeader;
	}
	
	
	
	
	
}
