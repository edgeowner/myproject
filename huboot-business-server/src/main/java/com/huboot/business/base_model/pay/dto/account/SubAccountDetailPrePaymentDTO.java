package com.huboot.business.base_model.pay.dto.account;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

//账户中心-子账户明细支付
public class SubAccountDetailPrePaymentDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    //支付类型
    private Integer paymentType;
    //订单id
    private Long detailId;
    @ApiModelProperty("使用红包的金额")
    private BigDecimal redPacketAmount;
    //支付类型
    private Integer isSharePay;

    public BigDecimal getRedPacketAmount() {
        return redPacketAmount;
    }

    public void setRedPacketAmount(BigDecimal redPacketAmount) {
        this.redPacketAmount = redPacketAmount;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public Integer getIsSharePay() {
        return isSharePay;
    }

    public void setIsSharePay(Integer isSharePay) {
        this.isSharePay = isSharePay;
    }
}

