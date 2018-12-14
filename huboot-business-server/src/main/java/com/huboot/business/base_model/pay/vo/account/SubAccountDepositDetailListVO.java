package com.huboot.business.base_model.pay.vo.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huboot.business.base_model.pay.CustomBigDecimalSerialize;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class SubAccountDepositDetailListVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 主健
    private Long id;
    // 创建时间
    private Timestamp createTime;
    // 创建时间
    private String createMonth;
    //主账户id
    private Long accountId;
    //子账户id
    //子账户id
    private Long subAccountId;
    //明细编号
    private String sn;
    //运算符号,code=COMM_OPERATOR_SYMBOL
    private Integer sign;
    //运算符号,code=COMM_OPERATOR_SYMBOL
    private String signName;
    //原金额
    @JsonSerialize(using = CustomBigDecimalSerialize.class)
    private BigDecimal originalAmount;
    //交易金额
    @JsonSerialize(using = CustomBigDecimalSerialize.class)
    private BigDecimal amountPaid;
    //变更后金额
    @JsonSerialize(using = CustomBigDecimalSerialize.class)
    private BigDecimal changedAmount;
    //子账户明细类型,code=SUB_ACCOUNT_DETAIL_TYPE
    private Integer type;
    //子账户明细类型,code=SUB_ACCOUNT_DETAIL_TYPE
    private String typeName;
    //子账户明细状态,code=SUB_ACCOUNT_DETAIL_STATUS
    private Integer status;
    //子账户明细状态,code=SUB_ACCOUNT_DETAIL_STATUS
    private String statusName;
    //备注信息
    private String remark;

    //支付单号
    private String paySn;
    private Integer payPlatform;
    private String payPlatformName;
    //支付成功日期
    private Date paymentDate;

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public Integer getPayPlatform() {
        return payPlatform;
    }

    public void setPayPlatform(Integer payPlatform) {
        this.payPlatform = payPlatform;
    }

    public String getPayPlatformName() {
        return payPlatformName;
    }

    public void setPayPlatformName(String payPlatformName) {
        this.payPlatformName = payPlatformName;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreateMonth() {
        return createMonth;
    }

    public void setCreateMonth(String createMonth) {
        this.createMonth = createMonth;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(Long subAccountId) {
        this.subAccountId = subAccountId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public BigDecimal getChangedAmount() {
        return changedAmount;
    }

    public void setChangedAmount(BigDecimal changedAmount) {
        this.changedAmount = changedAmount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}

