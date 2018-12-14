package com.huboot.business.base_model.pay.dto.account;

import java.io.Serializable;
import java.math.BigDecimal;

//账户中心-子账户明细
public class RedPacketAccountDetailAddDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    //账户id--如果需要是shopid，需要转换成accountid，可以调用IAccountService的findByShopId获得该值
    private Long accountId;
    //交易金额
    private BigDecimal amountPaid;
    //子账户明细类型,enum=subAccountDetailType
    private Integer type;
    //备注信息
    private String remark;
    //交易系统-订单编号
    private String tradeSn;
    //操作人
    private String operator;
    //操作人类型,code=SUB_ACCOUNT_DETAIL_OPERATOR_TYPE
    private Integer operatorType ;
    //操作人手机
    private String operatorPhone ;
    //交易来源
    private Integer tradeSource ;

    public Integer getTradeSource() {
        return tradeSource;
    }

    public void setTradeSource(Integer tradeSource) {
        this.tradeSource = tradeSource;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    public String getOperatorPhone() {
        return operatorPhone;
    }

    public void setOperatorPhone(String operatorPhone) {
        this.operatorPhone = operatorPhone;
    }
}

