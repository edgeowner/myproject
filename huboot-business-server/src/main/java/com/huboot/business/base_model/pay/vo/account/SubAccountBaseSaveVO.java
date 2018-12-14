package com.huboot.business.base_model.pay.vo.account;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

//@ApiModel(value = "账户中心-子账户基础信息", description = "账户中心-子账户基础信息")
public class SubAccountBaseSaveVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("店铺ID")
	private Long shopId ;
	@ApiModelProperty("店铺名称")
	private String shopname ;
	@ApiModelProperty("子账户类型,code=SUB_ACCOUNT_TYPE")
	private Integer type ;

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public void setType(Integer type){
		this.type = type;
	}

	public Integer getType() {
		return this.type;
	}

	
}

