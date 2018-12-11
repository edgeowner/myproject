package com.huboot.business.base_model.login.sso.client.dto.reset_pwd;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class ResetPwdDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "账号不能为空")
	private String account;
	
	@NotBlank(message = "旧密码不能为空")
	private String oldPassword;
	
	@NotBlank(message = "新密码不能为空")
	private String newPassword;
	
	@NotBlank(message = "加密机不能为空")
	private String passwordEncoder;

	@NotBlank(message = "系统来源不能为空")
	private String fromSys;

	@NotBlank(message = "校验码不能为空")
	private String securityCode;

	public String getFromSys() {
		return fromSys;
	}

	public void setFromSys(String fromSys) {
		this.fromSys = fromSys;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(String passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public String toString() {
		return "ResetPwdDTO{" +
				"account='" + account + '\'' +
				", oldPassword='" + oldPassword + '\'' +
				", newPassword='" + newPassword + '\'' +
				", passwordEncoder='" + passwordEncoder + '\'' +
				", fromSys='" + fromSys + '\'' +
				", securityCode='" + securityCode + '\'' +
				'}';
	}
}
