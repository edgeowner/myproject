package com.huboot.business.base_model.login.sso.client.dto.api_security;

import java.io.Serializable;
import java.util.List;

/**
 * 受保护的api配置dto
 * **/
public class ApiSecurityDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sysName;
	
	private String key;
	
	private List<String> roles;

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
