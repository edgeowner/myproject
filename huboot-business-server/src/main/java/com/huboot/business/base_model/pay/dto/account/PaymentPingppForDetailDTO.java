package com.huboot.business.base_model.pay.dto.account;


import com.huboot.business.base_model.pay.enums.CustomerPaymentTypeEnum;
import com.huboot.business.base_model.pay.enums.SubAccountDetailPaymentExtendCashAccountTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;

//支付
public class PaymentPingppForDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    //需要支付金额
    private BigDecimal amount;
    //支付类型
    private CustomerPaymentTypeEnum typeEnum;
    //支付id
    private Long paymentId;
    //支付sn
    private String paymentSn;
    //微信公众号支付，必填
    private String userOpenid;
    //收款人姓名
    private String userName;
    //账号
    private String cardNumber;
    //银行编码
    private String bankCode;
    //银行类型
    private SubAccountDetailPaymentExtendCashAccountTypeEnum accountType;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CustomerPaymentTypeEnum getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(CustomerPaymentTypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getUserOpenid() {
        return userOpenid;
    }

    public void setUserOpenid(String userOpenid) {
        this.userOpenid = userOpenid;
    }

    public String getPaymentSn() {
        return paymentSn;
    }

    public void setPaymentSn(String paymentSn) {
        this.paymentSn = paymentSn;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public SubAccountDetailPaymentExtendCashAccountTypeEnum getAccountType() {
        return accountType;
    }

    public void setAccountType(SubAccountDetailPaymentExtendCashAccountTypeEnum accountType) {
        this.accountType = accountType;
    }
}

