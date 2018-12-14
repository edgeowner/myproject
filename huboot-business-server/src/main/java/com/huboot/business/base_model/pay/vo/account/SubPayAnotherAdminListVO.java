package com.huboot.business.base_model.pay.vo.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huboot.business.base_model.pay.CustomBigDecimalSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//@ApiModel(value = "账户中心-子账户明细", description = "账户中心-子账户明细")
public class SubPayAnotherAdminListVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("代付记录ID")
	private Long payAnotherId ;
	@ApiModelProperty("申请流水号")
	private String applySn ;
	@ApiModelProperty("代付流水号")
	private String payAnotherSn ;
	@ApiModelProperty("提现申请金额")
	@JsonSerialize(using = CustomBigDecimalSerialize.class)
	private BigDecimal applyAmountPaid ;
	@ApiModelProperty("代付金额")
	@JsonSerialize(using = CustomBigDecimalSerialize.class)
	private BigDecimal payAnotherAmountPaid ;
	@ApiModelProperty("代付状态,code=SUB_ACCOUNT_DETAIL_STATUS")
	private Integer payAnotherStatus ;
	@ApiModelProperty("代付状态名称")
	private String payAnotherStatusName ;
	@ApiModelProperty("备注信息")
	private String remark ;
	@ApiModelProperty("提现申请时间")
	private Date applyCreateTime;
	@ApiModelProperty("代付时间")
	private Date payAnotherPaymentDate;
	@ApiModelProperty("操作人")
	private String payAnotherOperator ;

	@ApiModelProperty("公司名称")
	private String companyName ;

	@ApiModelProperty("申请人")
	private String applyOperatorName ;
	@ApiModelProperty("申请人手机号")
	private String applyOperatorPhone ;

	@ApiModelProperty("代付方式")
	private String paymentType;

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Long getPayAnotherId() {
		return payAnotherId;
	}

	public void setPayAnotherId(Long payAnotherId) {
		this.payAnotherId = payAnotherId;
	}

	public String getApplySn() {
		return applySn;
	}

	public void setApplySn(String applySn) {
		this.applySn = applySn;
	}

	public String getPayAnotherSn() {
		return payAnotherSn;
	}

	public void setPayAnotherSn(String payAnotherSn) {
		this.payAnotherSn = payAnotherSn;
	}

	public BigDecimal getApplyAmountPaid() {
		return applyAmountPaid;
	}

	public void setApplyAmountPaid(BigDecimal applyAmountPaid) {
		this.applyAmountPaid = applyAmountPaid;
	}

	public BigDecimal getPayAnotherAmountPaid() {
		return payAnotherAmountPaid;
	}

	public void setPayAnotherAmountPaid(BigDecimal payAnotherAmountPaid) {
		this.payAnotherAmountPaid = payAnotherAmountPaid;
	}

	public Integer getPayAnotherStatus() {
		return payAnotherStatus;
	}

	public void setPayAnotherStatus(Integer payAnotherStatus) {
		this.payAnotherStatus = payAnotherStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getApplyCreateTime() {
		return applyCreateTime;
	}

	public void setApplyCreateTime(Date applyCreateTime) {
		this.applyCreateTime = applyCreateTime;
	}

	public Date getPayAnotherPaymentDate() {
		return payAnotherPaymentDate;
	}

	public void setPayAnotherPaymentDate(Date payAnotherPaymentDate) {
		this.payAnotherPaymentDate = payAnotherPaymentDate;
	}

	public String getPayAnotherOperator() {
		return payAnotherOperator;
	}

	public void setPayAnotherOperator(String payAnotherOperator) {
		this.payAnotherOperator = payAnotherOperator;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPayAnotherStatusName() {
		return payAnotherStatusName;
	}

	public void setPayAnotherStatusName(String payAnotherStatusName) {
		this.payAnotherStatusName = payAnotherStatusName;
	}

	public String getApplyOperatorName() {
		return applyOperatorName;
	}

	public void setApplyOperatorName(String applyOperatorName) {
		this.applyOperatorName = applyOperatorName;
	}

	public String getApplyOperatorPhone() {
		return applyOperatorPhone;
	}

	public void setApplyOperatorPhone(String applyOperatorPhone) {
		this.applyOperatorPhone = applyOperatorPhone;
	}
}

