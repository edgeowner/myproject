package com.huboot.business.base_model.pay.vo.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huboot.business.base_model.pay.CustomBigDecimalSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

//@ApiModel(value = "押金账户", description = "押金账户")
public class SubAccountDepositListVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("买家店铺ID")
	private Long buyerShopId ;
	@ApiModelProperty("卖家店铺ID")
	private Long sellerShopId ;
	@ApiModelProperty("公司ID")
	private Long companyId ;
	@ApiModelProperty("logo原图路径")
	private String logoPath ;
	@ApiModelProperty("店铺经营模式,code=CUSTOMER_SHOP_BUSSINESS_TYPE")
	private Integer businessType ;
	@ApiModelProperty("店铺名称")
	private String name ;
	@ApiModelProperty("地区")
	private Long areaId ;
	@ApiModelProperty("联系地址")
	private String address ;
	@ApiModelProperty("默认联系人")
	private String defaultContract ;
	@ApiModelProperty("默认联系人电话")
	private String defaultphone ;

	@ApiModelProperty("押金账户余额")
	@JsonSerialize(using = CustomBigDecimalSerialize.class)
	private BigDecimal Balance ;
	@ApiModelProperty("押金账户状态,code=DEPOSIT_ACCOUNT_STATUS")
	private Integer status ;

	@ApiModelProperty("车辆分配数量")
	private Integer allotCartotal;
	@ApiModelProperty("押金是否有充值未支付，1：有，0：没有")
	private Integer isWaitForPayRecharge ;


	public void setId(Long id){
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

	public Long getBuyerShopId() {
		return buyerShopId;
	}

	public void setBuyerShopId(Long buyerShopId) {
		this.buyerShopId = buyerShopId;
	}

	public Long getSellerShopId() {
		return sellerShopId;
	}

	public void setSellerShopId(Long sellerShopId) {
		this.sellerShopId = sellerShopId;
	}

	public String getDefaultContract() {
		return defaultContract;
	}

	public void setDefaultContract(String defaultContract) {
		this.defaultContract = defaultContract;
	}

	public String getDefaultphone() {
		return defaultphone;
	}

	public void setDefaultphone(String defaultphone) {
		this.defaultphone = defaultphone;
	}

	public BigDecimal getBalance() {
		return Balance;
	}

	public void setBalance(BigDecimal balance) {
		Balance = balance;
	}

	public Integer getAllotCartotal() {
		return allotCartotal;
	}

	public void setAllotCartotal(Integer allotCartotal) {
		this.allotCartotal = allotCartotal;
	}

	public Integer getIsWaitForPayRecharge() {
		return isWaitForPayRecharge;
	}

	public void setIsWaitForPayRecharge(Integer isWaitForPayRecharge) {
		this.isWaitForPayRecharge = isWaitForPayRecharge;
	}
}

