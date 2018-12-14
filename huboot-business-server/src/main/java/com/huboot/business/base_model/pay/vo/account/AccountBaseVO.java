package com.huboot.business.base_model.pay.vo.account;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

//@ApiModel(value = "账户中心-账户基础信息表", description = "账户中心-账户基础信息表")
public class AccountBaseVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("账户id")
	private Long accountId ;
	@ApiModelProperty("店铺ID")
	private Long shopId ;
	@ApiModelProperty("店铺名称")
	private String shopName ;
	@ApiModelProperty("用户gid")
	private String userGid ;
	@ApiModelProperty("账户总资产")
	private BigDecimal totalAssets ;
	@ApiModelProperty("账户状态,code=ACCOUNT_STATUS")
	private Integer status ;
	@ApiModelProperty("更新版本号")
	private Integer version ;

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
	
	public void setShopId(Long shopId){
		this.shopId = shopId;
	}

	public Long getShopId() {
		return this.shopId;
	}
	
	public void setShopName(String shopName){
		this.shopName = shopName;
	}

	public String getShopName() {
		return this.shopName;
	}
	
	public void setUserGid(String userGid){
		this.userGid = userGid;
	}

	public String getUserGid() {
		return this.userGid;
	}
	
	public void setTotalAssets(BigDecimal totalAssets){
		this.totalAssets = totalAssets;
	}

	public BigDecimal getTotalAssets() {
		return this.totalAssets;
	}
	
	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}
	
	public void setVersion(Integer version){
		this.version = version;
	}

	public Integer getVersion() {
		return this.version;
	}
	
}

