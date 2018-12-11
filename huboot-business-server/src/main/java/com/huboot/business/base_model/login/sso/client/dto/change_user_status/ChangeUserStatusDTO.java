package com.huboot.business.base_model.login.sso.client.dto.change_user_status;

import com.huboot.business.base_model.login.sso.client.dto.BaseUser;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


public class ChangeUserStatusDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotBlank( message = "gid不能为空")
	private String gid;
	
	private BaseUser.UserStatus status;

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public BaseUser.UserStatus getStatus() {
		return status;
	}

	public void setStatus(BaseUser.UserStatus status) {
		this.status = status;
	}
	
	

}
