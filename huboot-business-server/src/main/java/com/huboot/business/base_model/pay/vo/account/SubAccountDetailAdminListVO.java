package com.huboot.business.base_model.pay.vo.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huboot.business.base_model.pay.CustomBigDecimalSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

//@ApiModel(value = "账户中心-子账户明细", description = "账户中心-子账户明细")
public class SubAccountDetailAdminListVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("主账户id")
	private Long accountId ;
	@ApiModelProperty("子账户id")
	private Long subAccountId ;
	@ApiModelProperty("明细编号")
	private String sn ;
	@ApiModelProperty("符号（+，-）")
	private String sign ;
	@ApiModelProperty("原金额")
	@JsonSerialize(using = CustomBigDecimalSerialize.class)
	private BigDecimal originalAmount ;
	@ApiModelProperty("交易金额")
	@JsonSerialize(using = CustomBigDecimalSerialize.class)
	private BigDecimal amountPaid ;
	@ApiModelProperty("变更后金额")
	@JsonSerialize(using = CustomBigDecimalSerialize.class)
	private BigDecimal changedAmount ;
	@ApiModelProperty("支付成功日期")
	private Date paymentDate ;
	@ApiModelProperty("子账户明细类型,code=SUB_ACCOUNT_DETAIL_TYPE")
	private Integer type ;
	@ApiModelProperty("子账户明细类型名称")
	private String typeName ;
	@ApiModelProperty("子账户明细状态,code=SUB_ACCOUNT_DETAIL_STATUS")
	private Integer status ;
	@ApiModelProperty("子账户明细状态名称")
	private String statusName ;
	@ApiModelProperty("备注信息")
	private String remark ;
	@ApiModelProperty("创建时间")
	private Timestamp createTime;
	@ApiModelProperty("操作时间")
	private Date modifyTime;
	@ApiModelProperty("操作人")
	private String operator ;

	@ApiModelProperty("公司名称")
	private String companyName ;

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

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
	
	public void setSn(String sn){
		this.sn = sn;
	}

	public String getSn() {
		return this.sn;
	}
	
	public void setSign(String sign){
		this.sign = sign;
	}

	public String getSign() {
		return this.sign;
	}
	
	public void setOriginalAmount(BigDecimal originalAmount){
		this.originalAmount = originalAmount;
	}

	public BigDecimal getOriginalAmount() {
		return this.originalAmount;
	}
	
	public void setAmountPaid(BigDecimal amountPaid){
		this.amountPaid = amountPaid;
	}

	public BigDecimal getAmountPaid() {
		return this.amountPaid;
	}
	
	public void setChangedAmount(BigDecimal changedAmount){
		this.changedAmount = changedAmount;
	}

	public BigDecimal getChangedAmount() {
		return this.changedAmount;
	}
	
	public void setPaymentDate(Date paymentDate){
		this.paymentDate = paymentDate;
	}

	public Date getPaymentDate() {
		return this.paymentDate;
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
	
	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}

