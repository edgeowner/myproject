package com.huboot.business.base_model.pay.vo.account;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

//@ApiModel(value = "账户中心-子账户", description = "账户中心-子账户")
public class SubAccountVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("账户id")
	private Long accountId ;

	public void setId(Long id){
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setAccountId(Long accountId){
		this.accountId = accountId;
	}

	public Long getAccountId() {
		return this.accountId;
	}

}

