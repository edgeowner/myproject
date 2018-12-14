package com.huboot.business.base_model.pay.dto.account;

import java.io.Serializable;
import java.math.BigDecimal;

//账户中心-子账户明细
public class RedPacketAccountDetailAddForOrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    //账户id--如果需要是shopid，需要转换成accountid，可以调用IAccountService的findByShopId获得该值
    //创建订单支付时，传入买家的accountid，就是sellerAccountId
    private Long accountId;
    //交易金额
    private BigDecimal amountPaid;
    //备注信息
    private String remark;
    //交易系统-订单编号
    private String tradeSn;
    //操作人
    private String operator;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getTradeSn() {
        return tradeSn;
    }

    public void setTradeSn(String tradeSn) {
        this.tradeSn = tradeSn;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

