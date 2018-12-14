package com.huboot.business.base_model.pay.vo.account;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

//@ApiModel(value = "账户中心-子账户明细支付", description = "账户中心-子账户明细支付")
public class SubAccountDetailPaymentVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("子账户明细id:兼容，id和sn至少传一个")
    private Long subAccountDetailId;
    @ApiModelProperty("会员支付类型,1-线下转账,2-微信公众号支付,3-微信原生扫码支付,4-微信app支付,10-红包支付,11-申请代付")
    private Integer paymentType;
    @ApiModelProperty("使用红包的金额")
    private BigDecimal redPacketAmount;

    public BigDecimal getRedPacketAmount() {
        return redPacketAmount;
    }

    public void setRedPacketAmount(BigDecimal redPacketAmount) {
        this.redPacketAmount = redPacketAmount;
    }

    public Long getSubAccountDetailId() {
        return subAccountDetailId;
    }

    public void setSubAccountDetailId(Long subAccountDetailId) {
        this.subAccountDetailId = subAccountDetailId;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }
}

