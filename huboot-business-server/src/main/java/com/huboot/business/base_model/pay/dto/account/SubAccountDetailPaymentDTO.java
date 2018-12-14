package com.huboot.business.base_model.pay.dto.account;


import com.huboot.business.base_model.pay.domain.account.SubAccountDetailPaymentDomain;

import java.io.Serializable;

//账户中心-子账户明细
public class SubAccountDetailPaymentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private SubAccountDetailPaymentDomain subAccountDetailPaymentDomain;

    public SubAccountDetailPaymentDomain getSubAccountDetailPaymentDomain() {
        return subAccountDetailPaymentDomain;
    }

    public void setSubAccountDetailPaymentDomain(SubAccountDetailPaymentDomain subAccountDetailPaymentDomain) {
        this.subAccountDetailPaymentDomain = subAccountDetailPaymentDomain;
    }
}

