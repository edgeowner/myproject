package com.huboot.business.base_model.pay.vo.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huboot.business.base_model.pay.CustomBigDecimalSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

//@ApiModel(value = "账户中心-子账户明细", description = "账户中心-子账户明细")
public class SubAccountBalanceDetailIndexVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("提现总额")
	@JsonSerialize(using = CustomBigDecimalSerialize.class)
	private BigDecimal cashMoneyTotal ;
	@JsonSerialize(using = CustomBigDecimalSerialize.class)
	@ApiModelProperty("代付总额")
	private BigDecimal payForAnotherMoneyTotal ;
	@JsonSerialize(using = CustomBigDecimalSerialize.class)
	@ApiModelProperty("手续费总额")
	private BigDecimal poundageTotal ;
	@ApiModelProperty("提现申请数量")
	private Integer applyTotalNum ;
	@ApiModelProperty("状态")
	private Integer status ;

	public BigDecimal getCashMoneyTotal() {
		return cashMoneyTotal;
	}

	public void setCashMoneyTotal(BigDecimal cashMoneyTotal) {
		this.cashMoneyTotal = cashMoneyTotal;
	}

	public BigDecimal getPayForAnotherMoneyTotal() {
		return payForAnotherMoneyTotal;
	}

	public void setPayForAnotherMoneyTotal(BigDecimal payForAnotherMoneyTotal) {
		this.payForAnotherMoneyTotal = payForAnotherMoneyTotal;
	}

	public BigDecimal getPoundageTotal() {
		return poundageTotal;
	}

	public void setPoundageTotal(BigDecimal poundageTotal) {
		this.poundageTotal = poundageTotal;
	}

	public Integer getApplyTotalNum() {
		return applyTotalNum;
	}

	public void setApplyTotalNum(Integer applyTotalNum) {
		this.applyTotalNum = applyTotalNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}

