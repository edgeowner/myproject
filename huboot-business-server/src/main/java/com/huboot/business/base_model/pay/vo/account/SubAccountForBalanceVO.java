package com.huboot.business.base_model.pay.vo.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huboot.business.base_model.pay.CustomBigDecimalSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

//@ApiModel(value = "账户中心-子账户", description = "账户中心-子账户")
public class SubAccountForBalanceVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("子账户余额")
    @JsonSerialize(using = CustomBigDecimalSerialize.class)
    private BigDecimal balance;
    @ApiModelProperty("子账户可用余额")
    @JsonSerialize(using = CustomBigDecimalSerialize.class)
    private BigDecimal usableBalance;
    @ApiModelProperty("子账户类型")
    private Integer type;
    @ApiModelProperty("账户状态:1-无效；2-有效")
    private Integer status;
    @ApiModelProperty("账户状态值")
    private String statusName;
    @ApiModelProperty("银行名称")
    private String bankName;
    @ApiModelProperty("银行logo")
    private String bankLogoPath;
    @ApiModelProperty("银行卡尾号")
    private String number;
    @ApiModelProperty("能否提现：1-能，2-不能")
    private Integer canCash;

    public BigDecimal getUsableBalance() {
        return usableBalance;
    }

    public void setUsableBalance(BigDecimal usableBalance) {
        this.usableBalance = usableBalance;
    }

    public Integer getCanCash() {
        return canCash;
    }

    public void setCanCash(Integer canCash) {
        this.canCash = canCash;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankLogoPath() {
        return bankLogoPath;
    }

    public void setBankLogoPath(String bankLogoPath) {
        this.bankLogoPath = bankLogoPath;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}

