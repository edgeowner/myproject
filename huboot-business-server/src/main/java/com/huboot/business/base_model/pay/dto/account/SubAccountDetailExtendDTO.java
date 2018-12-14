package com.huboot.business.base_model.pay.dto.account;



import com.huboot.business.base_model.pay.domain.account.SubAccountDetailExtendDomain;
import com.huboot.business.base_model.pay.domain.account.extend.SubAccountDetailCashExtendDomain;
import com.huboot.business.base_model.pay.domain.account.extend.SubAccountDetailPayItemsExtendDomain;
import com.huboot.business.base_model.pay.domain.account.extend.SubAccountDetailPaymentExtendDomain;
import com.huboot.business.mybatis.AbstractDomain;

import java.io.Serializable;
import java.util.List;

/**
*账户中心-子账户明细扩展
*/
public class SubAccountDetailExtendDTO extends AbstractDomain<Long> implements Serializable {
    private static final long serialVersionUID = 1L;
    //扩展信息
    private SubAccountDetailExtendDomain subAccountDetailExtendDomain;
    //结构化后的提现信息
    private SubAccountDetailCashExtendDomain subAccountDetailCashExtendDomain;
    //结构化后的支付控制的信息
    private SubAccountDetailPaymentExtendDomain subAccountDetailPaymentExtendDomain;
    //结构化后的支付控制的信息
    private List<SubAccountDetailPayItemsExtendDomain> payItemsExtendDomainList;

    public List<SubAccountDetailPayItemsExtendDomain> getPayItemsExtendDomainList() {
        return payItemsExtendDomainList;
    }

    public void setPayItemsExtendDomainList(List<SubAccountDetailPayItemsExtendDomain> payItemsExtendDomainList) {
        this.payItemsExtendDomainList = payItemsExtendDomainList;
    }

    public SubAccountDetailPaymentExtendDomain getSubAccountDetailPaymentExtendDomain() {
        return subAccountDetailPaymentExtendDomain;
    }

    public void setSubAccountDetailPaymentExtendDomain(SubAccountDetailPaymentExtendDomain subAccountDetailPaymentExtendDomain) {
        this.subAccountDetailPaymentExtendDomain = subAccountDetailPaymentExtendDomain;
    }

    public SubAccountDetailExtendDomain getSubAccountDetailExtendDomain() {
        return subAccountDetailExtendDomain;
    }

    public void setSubAccountDetailExtendDomain(SubAccountDetailExtendDomain subAccountDetailExtendDomain) {
        this.subAccountDetailExtendDomain = subAccountDetailExtendDomain;
    }

    public SubAccountDetailCashExtendDomain getSubAccountDetailCashExtendDomain() {
        return subAccountDetailCashExtendDomain;
    }

    public void setSubAccountDetailCashExtendDomain(SubAccountDetailCashExtendDomain subAccountDetailCashExtendDomain) {
        this.subAccountDetailCashExtendDomain = subAccountDetailCashExtendDomain;
    }
}

