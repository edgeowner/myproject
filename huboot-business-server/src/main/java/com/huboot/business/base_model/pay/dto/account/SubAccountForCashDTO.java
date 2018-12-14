package com.huboot.business.base_model.pay.dto.account;

import com.huboot.business.base_model.pay.enums.SubAccountDetailPaymentExtendCashAccountTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;

//账户中心-子账户
public class SubAccountForCashDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    //主账户id
    private Long accountId;
    //交易金额
    private BigDecimal amountPaid;
    //收款人名称
    private String userName;
    //收款人银行
    private Long openBankId;
    //收款人账号
    private String cardNumber;
    //操作人
    private String operator ;
    //操作人手机
    private String operatorPhone ;
    //付款类型：
    private SubAccountDetailPaymentExtendCashAccountTypeEnum accountType;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getOpenBankId() {
        return openBankId;
    }

    public void setOpenBankId(Long openBankId) {
        this.openBankId = openBankId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getOperatorPhone() {
        return operatorPhone;
    }

    public void setOperatorPhone(String operatorPhone) {
        this.operatorPhone = operatorPhone;
    }
}

