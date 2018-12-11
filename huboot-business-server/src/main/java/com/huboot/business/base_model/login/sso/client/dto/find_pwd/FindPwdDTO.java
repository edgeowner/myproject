package com.huboot.business.base_model.login.sso.client.dto.find_pwd;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


public class FindPwdDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "账号不能为空")
	private String account;
	
	@NotBlank(message = "新密码不能为空")
	private String newPassword;

	@NotBlank(message = "加密机不能为空")
	private String passwordEncoder;

	@NotBlank(message = "系统来源不能为空")
	private String fromSys;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(String passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public String getFromSys() {
		return fromSys;
	}

	public void setFromSys(String fromSys) {
		this.fromSys = fromSys;
	}

	@Override
	public String toString() {
		return "FindPwdDTO{" +
				"account='" + account + '\'' +
				", newPassword='" + newPassword + '\'' +
				", passwordEncoder='" + passwordEncoder + '\'' +
				", fromSys='" + fromSys + '\'' +
				'}';
	}
}
