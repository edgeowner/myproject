package com.huboot.business.base_model.pay.vo.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huboot.business.base_model.pay.CustomBigDecimalSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

//@ApiModel(value = "账户中心-子账户明细", description = "账户中心-子账户明细")
public class SubAccountDetailAddForSellerVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("子账户id")
    private Long buyerSubAccountId;
    @ApiModelProperty("交易金额")
    @JsonSerialize(using = CustomBigDecimalSerialize.class)
    private BigDecimal amountPaid;
    @ApiModelProperty("备注信息")
    private String remark;

    public Long getBuyerSubAccountId() {
        return buyerSubAccountId;
    }

    public void setBuyerSubAccountId(Long buyerSubAccountId) {
        this.buyerSubAccountId = buyerSubAccountId;
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
}

