package com.huboot.business.base_model.pay.dto.account;

import java.io.Serializable;

//账户中心-子账户明细
public class SubAccountDetailForPayAnotherDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    //唯一标识
    private Long id;
    //备注信息
    private String remark;
    //操作人
    private String operator ;
    //代付方式
    private Integer paymentType;
    //银行流水号
    private String bankFlow;

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getBankFlow() {
        return bankFlow;
    }

    public void setBankFlow(String bankFlow) {
        this.bankFlow = bankFlow;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

