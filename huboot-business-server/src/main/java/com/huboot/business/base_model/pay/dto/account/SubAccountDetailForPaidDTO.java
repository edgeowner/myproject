package com.huboot.business.base_model.pay.dto.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//账户中心-子账户明细
public class SubAccountDetailForPaidDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    //唯一标识
    private Long detailId;
    //支付成功日期
    private Date paymentDate;
    //操作人
    private String operator ;
    //支付id
    private Long paymentId;
    //支付id
    private Integer paymentType;

    private String sn;

    private BigDecimal paidAmount;

    //是否代付：1，是；2.否
    private Integer isSharePay ;

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Integer getIsSharePay() {
        return isSharePay;
    }

    public void setIsSharePay(Integer isSharePay) {
        this.isSharePay = isSharePay;
    }
}

