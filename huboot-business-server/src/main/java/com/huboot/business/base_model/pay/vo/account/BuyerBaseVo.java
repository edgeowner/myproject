package com.huboot.business.base_model.pay.vo.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huboot.business.base_model.pay.CustomBigDecimalSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

//账户中心-押金账户扩展
public class BuyerBaseVo implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("子账户id")
	private Long subAccountId ;
	@ApiModelProperty("店铺ID")
	private Long shopId ;
	@ApiModelProperty("店铺名称")
	private String shopName ;
	@ApiModelProperty("账户余额")
	@JsonSerialize(using = CustomBigDecimalSerialize.class)
	private BigDecimal balance ;
	@ApiModelProperty("账户状态")
	private Integer status ;

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getSubAccountId() {
		return subAccountId;
	}

	public void setSubAccountId(Long subAccountId) {
		this.subAccountId = subAccountId;
	}
}

