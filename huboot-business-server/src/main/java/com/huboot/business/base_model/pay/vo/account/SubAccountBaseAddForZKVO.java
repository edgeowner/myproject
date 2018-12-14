package com.huboot.business.base_model.pay.vo.account;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class
SubAccountBaseAddForZKVO implements Serializable {

	@ApiModelProperty("用户gid")
	private String userGid ;
	@ApiModelProperty("公司uid")
	private String compnayUid ;

	public String getUserGid() {
		return userGid;
	}

	public void setUserGid(String userGid) {
		this.userGid = userGid;
	}

	public String getCompnayUid() {
		return compnayUid;
	}

	public void setCompnayUid(String compnayUid) {
		this.compnayUid = compnayUid;
	}

}

