package com.huboot.business.base_model.login.sso.client.dto.logout;

import com.huboot.business.base_model.login.sso.client.dto.login.ClientInfo;
import com.huboot.business.base_model.login.sso.client.dto.login.RequestHeader;


import javax.validation.constraints.NotBlank;
import java.io.Serializable;


public class LogoutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "账号不能为空")
	private String account;
	
	private ClientInfo clientInfo;
	
	private RequestHeader requestHeader;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	@Override
	public String toString() {
		return "LogoutDto [account=" + account + ", clientInfo=" + clientInfo + ", requestHeader=" + requestHeader
				+ "]";
	}

	
	
}
