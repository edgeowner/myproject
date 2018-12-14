package com.huboot.business.base_model.pay.dto.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

//账户中心-押金账户扩展
public class BuyerBaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	//子账户id
	private Long subAccountId ;
	//店铺id
	private BigInteger shopId ;
	//店铺名称
	private String shopName ;
	//账户余额
	private BigDecimal balance ;
	//账户状态
	private Integer status ;


	public BigInteger getShopId() {
		return shopId;
	}

	public void setShopId(BigInteger shopId) {
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

