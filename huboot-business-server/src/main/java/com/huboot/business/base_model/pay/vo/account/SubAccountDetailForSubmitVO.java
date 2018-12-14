package com.huboot.business.base_model.pay.vo.account;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

//@ApiModel(value = "账户中心-子账户明细", description = "账户中心-子账户明细")
public class SubAccountDetailForSubmitVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("detailId")
    private Long detailId;
    @ApiModelProperty("备注信息")
    private String remark;
    @ApiModelProperty("代付方式")
    private Integer paymentType;
    @ApiModelProperty("银行流水号")
    private String bankFlow;

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getBankFlow() {
        return bankFlow;
    }

    public void setBankFlow(String bankFlow) {
        this.bankFlow = bankFlow;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

