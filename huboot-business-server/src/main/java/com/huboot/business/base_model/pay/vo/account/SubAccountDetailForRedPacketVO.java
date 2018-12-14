package com.huboot.business.base_model.pay.vo.account;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

//@ApiModel(value = "账户中心-子账户明细", description = "账户中心-子账户明细")
public class SubAccountDetailForRedPacketVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("唯一标识")
    private Long id;
    @ApiModelProperty("主账户id")
    private Long accountId;
    @ApiModelProperty("子账户id")
    private Long subAccountId;
    @ApiModelProperty("金额")
//    @JsonSerialize(using = CustomBigDecimalSerialize.class)
    private BigDecimal balance;
    @ApiModelProperty("可用金额")
//    @JsonSerialize(using = CustomBigDecimalSerialize.class)
    private BigDecimal usableBalance;
    @ApiModelProperty("是否显示：1-是，2-否")
    private Integer show;
    @ApiModelProperty("已经使用红包金额")
    private BigDecimal usedBalance;

    public BigDecimal getUsedBalance() {
        return usedBalance;
    }

    public void setUsedBalance(BigDecimal usedBalance) {
        this.usedBalance = usedBalance;
    }

    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getUsableBalance() {
        return usableBalance;
    }

    public void setUsableBalance(BigDecimal usableBalance) {
        this.usableBalance = usableBalance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}

