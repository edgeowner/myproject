package com.huboot.business.base_model.pay.dto.account;

import java.io.Serializable;
import java.math.BigDecimal;

//账户中心-子账户明细
public class SubAccountDetailAddDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    //子账户id
    private Long subAccountId;
    //交易金额
    private BigDecimal amountPaid;
    //子账户明细类型,enum=subAccountDetailType
    private Integer type;
    //备注信息
    private String remark;
    //交易系统-订单编号
    private String tradeSn;

    public String getTradeSn() {
        return tradeSn;
    }

    public void setTradeSn(String tradeSn) {
        this.tradeSn = tradeSn;
    }

    public Long getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(Long subAccountId) {
        this.subAccountId = subAccountId;
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
}

