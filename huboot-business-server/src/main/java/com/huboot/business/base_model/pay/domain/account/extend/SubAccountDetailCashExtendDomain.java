package com.huboot.business.base_model.pay.domain.account.extend;


import com.huboot.business.mybatis.AbstractDomain;

import java.io.Serializable;

/**
*账户中心-子账户明细扩展
*/
public class SubAccountDetailCashExtendDomain extends AbstractDomain<Long> implements Serializable {
    private static final long serialVersionUID = 1L;
    //收款人名称
    private String userName;
    //收款人银行
    private String openBank;
    //收款人银行编码
    private String openBankCode;
    //收款人账号
    private String cardNumber;
    //收款人银行编码-pingpp的编码
    private String openBankPingppCode;
    //付款类型：SubAccountDetailPaymentExtendCashAccountTypeEnum
    private Integer accountType;
    //开户支行
    private String subBank ;

    public String getSubBank() {
        return subBank;
    }

    public void setSubBank(String subBank) {
        this.subBank = subBank;
    }

    public String getOpenBankPingppCode() {
        return openBankPingppCode;
    }

    public void setOpenBankPingppCode(String openBankPingppCode) {
        this.openBankPingppCode = openBankPingppCode;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOpenBankCode() {
        return openBankCode;
    }

    public void setOpenBankCode(String openBankCode) {
        this.openBankCode = openBankCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

}

