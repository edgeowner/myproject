package com.huboot.business.base_model.pay.domain.account.extend;


import com.huboot.business.mybatis.AbstractDomain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
*账户中心-子账户明细扩展
*/
public class SubAccountDetailPayItemsExtendDomain extends AbstractDomain<Long> implements Serializable {

    //交易金额
    private BigDecimal amount;
    //交易单号
    private String tradeSn;
    //订单类型
    private Integer orderType;
    //说明
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTradeSn() {
        return tradeSn;
    }

    public void setTradeSn(String tradeSn) {
        this.tradeSn = tradeSn;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }
}

