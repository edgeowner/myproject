package com.huboot.business.base_model.pay.vo.account;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

//@ApiModel(value = "账户中心-子账户明细", description = "账户中心-子账户明细")
public class SubAccountDetailForRedPacketOrderVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("主账户id")
    private Long accountId;
    @ApiModelProperty("子账户id")
    private Long subAccountId;
    @ApiModelProperty("红包金额")
//    @JsonSerialize(using = CustomBigDecimalSerialize.class)
    private BigDecimal balance;

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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}

