package com.huboot.business.base_model.pay.dto.account;


import com.huboot.business.base_model.pay.enums.SubAccountDetailTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;

//账户中心-子账户
public class SubAccountForRefundDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    //主账户id
    private Long sellerAccountId;
    //交易金额
    private BigDecimal amountPaid;
    //操作人
    private String operator;
    //子账户明细类型
    private SubAccountDetailTypeEnum type;
    //订单号
    private String tradeSn;

    public Long getSellerAccountId() {
        return sellerAccountId;
    }

    public void setSellerAccountId(Long sellerAccountId) {
        this.sellerAccountId = sellerAccountId;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public SubAccountDetailTypeEnum getType() {
        return type;
    }

    public void setType(SubAccountDetailTypeEnum type) {
        this.type = type;
    }

    public String getTradeSn() {
        return tradeSn;
    }

    public void setTradeSn(String tradeSn) {
        this.tradeSn = tradeSn;
    }
}

