package com.huboot.business.base_model.pay.vo.account;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

//@ApiModel(value = "账户中心-押金账户扩展", description = "账户中心-押金账户扩展")
public class SubAccountDepositVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("子账户id")
	private Long subAccountId ;
	@ApiModelProperty("卖家店铺ID")
	private Long sellerShopId ;
	@ApiModelProperty("卖家店铺名称")
	private String sellerShopName ;

	public void setId(Long id){
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}
	
	public void setSubAccountId(Long subAccountId){
		this.subAccountId = subAccountId;
	}

	public Long getSubAccountId() {
		return this.subAccountId;
	}
	
	public void setSellerShopId(Long sellerShopId){
		this.sellerShopId = sellerShopId;
	}

	public Long getSellerShopId() {
		return this.sellerShopId;
	}
	
	public void setSellerShopName(String sellerShopName){
		this.sellerShopName = sellerShopName;
	}

	public String getSellerShopName() {
		return this.sellerShopName;
	}
	
}

