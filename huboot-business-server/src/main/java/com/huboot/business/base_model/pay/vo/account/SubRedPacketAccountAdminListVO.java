package com.huboot.business.base_model.pay.vo.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huboot.business.base_model.pay.CustomBigDecimalSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//@ApiModel(value = "账户中心-子账户基础信息", description = "账户中心-子账户基础信息")
public class SubRedPacketAccountAdminListVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("账户id")
	private Long accountId ;
	@ApiModelProperty("子账户id")
	private Long subAccountId ;
	@ApiModelProperty("子账户余额")
	@JsonSerialize(using = CustomBigDecimalSerialize.class)
	private BigDecimal balance ;
	@ApiModelProperty("子账户类型,code=SUB_ACCOUNT_TYPE")
	private Integer type ;
	@ApiModelProperty("账户状态,code=ACCOUNT_STATUS")
	private Integer status ;
	@ApiModelProperty("账户状态名称")
	private String statusName ;
	@ApiModelProperty("公司名称")
	private String companyName ;
	@ApiModelProperty("修改时间")
	protected Date modifyTime;

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
	
	public void setSubAccountId(Long subAccountId){
		this.subAccountId = subAccountId;
	}

	public Long getSubAccountId() {
		return this.subAccountId;
	}
	
	public void setBalance(BigDecimal balance){
		this.balance = balance;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}
	
	public void setType(Integer type){
		this.type = type;
	}

	public Integer getType() {
		return this.type;
	}
	
	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}

