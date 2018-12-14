package com.huboot.business.base_model.pay.vo.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huboot.business.base_model.pay.CustomBigDecimalSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

//@ApiModel(value = "账户中心-子账户", description = "账户中心-子账户")
public class SubAccountForCashVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("提现金额")
    @JsonSerialize(using = CustomBigDecimalSerialize.class)
    private BigDecimal amount_paid;

    public BigDecimal getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(BigDecimal amount_paid) {
        this.amount_paid = amount_paid;
    }
}

