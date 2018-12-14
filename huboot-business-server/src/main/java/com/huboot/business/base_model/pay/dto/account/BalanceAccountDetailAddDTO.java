package com.huboot.business.base_model.pay.dto.account;


import com.huboot.business.base_model.pay.enums.SubAccountDetailPaymentExtendCashAccountTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;

//账户中心-子账户明细
public class BalanceAccountDetailAddDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    //子账户id
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

    //收款人名称-提现
    private String userName;
    //收款人银行-提现
    private Long openBankId;
    //收款人账号-提现
    private String cardNumber;
    //付款类型-提现
    private SubAccountDetailPaymentExtendCashAccountTypeEnum accountType;
    //原金额
    private BigDecimal originalAmount ;
    //变更后金额
    private BigDecimal changedAmount ;
    //开户支行
    private String subBank ;

    public String getSubBank() {
        return subBank;
    }

    public void setSubBank(String subBank) {
        this.subBank = subBank;
    }

    public SubAccountDetailPaymentExtendCashAccountTypeEnum getAccountType() {
        return accountType;
    }

    public void setAccountType(SubAccountDetailPaymentExtendCashAccountTypeEnum accountType) {
        this.accountType = accountType;
    }

    public Long getOpenBankId() {
        return openBankId;
    }

    public void setOpenBankId(Long openBankId) {
        this.openBankId = openBankId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getChangedAmount() {
        return changedAmount;
    }

    public void setChangedAmount(BigDecimal changedAmount) {
        this.changedAmount = changedAmount;
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

