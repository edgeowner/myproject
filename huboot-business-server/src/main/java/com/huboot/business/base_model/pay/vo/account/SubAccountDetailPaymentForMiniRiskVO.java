package com.huboot.business.base_model.pay.vo.account;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

//@ApiModel(value = "账户中心-子账户明细支付", description = "账户中心-子账户明细支付")
public class SubAccountDetailPaymentForMiniRiskVO implements Serializable {

    @ApiModelProperty("子账户明细id:兼容，id和sn至少传一个")
    private String subAccountDetailSn;
    @ApiModelProperty("会员支付类型,1-线下转账,2-微信公众号支付,3-微信原生扫码支付,4-微信app支付,10-红包支付,11-申请代付, 12-微信小程序支付")
    private Integer paymentType;

    public String getSubAccountDetailSn() {
        return subAccountDetailSn;
    }

    public void setSubAccountDetailSn(String subAccountDetailSn) {
        this.subAccountDetailSn = subAccountDetailSn;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

}

