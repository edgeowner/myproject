package com.huboot.business.base_model.pay.vo.account;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

//@ApiModel(value = "账户中心-子账户明细", description = "账户中心-子账户明细")
public class SubPayAnotherAdminCancelVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("代付记录ID")
	private Long payAnotherId ;
	@ApiModelProperty("撤销原因")
	private String cancelReason ;

	public Long getPayAnotherId() {
		return payAnotherId;
	}

	public void setPayAnotherId(Long payAnotherId) {
		this.payAnotherId = payAnotherId;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
}

