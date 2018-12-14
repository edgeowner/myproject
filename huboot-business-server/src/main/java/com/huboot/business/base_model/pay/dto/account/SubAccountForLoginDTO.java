package com.huboot.business.base_model.pay.dto.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//账户中心-子账户
public class SubAccountForLoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    //主账户id
    private Long sellerAccountId;
    //交易金额
    private BigDecimal amountPaid;
    //操作人
    private String operator ;
    //支付成功日期
    private Date paymentDate ;

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

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
}

