package com.huboot.business.base_model.pay.dto.account;

import java.io.Serializable;
import java.math.BigDecimal;

//账户中心-子账户明细
public class SubAccountDetailAddForOrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    //账户id--如果需要是shopid，需要转换成accountid，可以调用IAccountService的findByShopId获得该值
    //创建订单支付时，传入买家的accountid，就是buyerAccountId
    private Long buyerAccountId;
    //创建订单支付时，传入买家的accountid，就是sellerAccountId
    private Long sellerAccountId;
    //交易金额
    private BigDecimal amountPaid;
    //备注信息
    private String remark;
    //交易系统-订单编号
    private String tradeSn;
    //操作人
    private String operator;
    //使用红包的最大值
    private BigDecimal maxRedPacketAmount;

    public BigDecimal getMaxRedPacketAmount() {
        return maxRedPacketAmount;
    }

    public void setMaxRedPacketAmount(BigDecimal maxRedPacketAmount) {
        this.maxRedPacketAmount = maxRedPacketAmount;
    }

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

    public Long getBuyerAccountId() {
        return buyerAccountId;
    }

    public void setBuyerAccountId(Long buyerAccountId) {
        this.buyerAccountId = buyerAccountId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

