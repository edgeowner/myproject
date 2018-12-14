package com.huboot.business.base_model.pay.dto.account;

import java.io.Serializable;

//账户中心-子账户明细支付PINGPP
public class SubAccountDetailPaymentTransferDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //支付id
    private Long paymentId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

}

