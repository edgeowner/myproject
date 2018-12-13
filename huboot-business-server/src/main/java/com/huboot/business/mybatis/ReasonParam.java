package com.huboot.business.mybatis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value="原因参数")
public class ReasonParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7435870026953197952L;
	@ApiModelProperty(value="原因")
	private String reason;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
