package com.huboot.business.base_model.pay.dto.account;

import java.io.Serializable;
import java.math.BigDecimal;

//账户中心-子账户明细
public class SubAccountDetailAddForZKOrderDTO implements Serializable {
    //创建订单支付时
    private Long sellerAccountId;
    //交易金额
    private BigDecimal amountPaid;
    //备注信息
    private String remark;
    //交易系统-订单编号
    private String tradeSn;
    //买家账户id
    private Long buyerAccointId;
    //操作人
    private String operator;
    //操作人
    private Integer tradeType;
    //支付成功后是否需要冻结资金，1-是,2-否
    private Integer needFreezeAfterSuccess;

    public Integer getNeedFreezeAfterSuccess() {
        return needFreezeAfterSuccess;
    }

    public void setNeedFreezeAfterSuccess(Integer needFreezeAfterSuccess) {
        this.needFreezeAfterSuccess = needFreezeAfterSuccess;
    }

    public Long getBuyerAccointId() {
        return buyerAccointId;
    }

    public void setBuyerAccointId(Long buyerAccointId) {
        this.buyerAccointId = buyerAccointId;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
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

    public String getTradeSn() {
        return tradeSn;
    }

    public void setTradeSn(String tradeSn) {
        this.tradeSn = tradeSn;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}

