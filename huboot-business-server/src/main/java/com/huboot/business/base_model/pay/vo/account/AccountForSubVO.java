package com.huboot.business.base_model.pay.vo.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

//@ApiModel(value = "账户中心-账户", description = "账户中心-账户")
public class AccountForSubVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("子账户余额")
    //@JsonSerialize(using = CustomBigDecimalSerialize.class)
    private BigDecimal balance;
    @ApiModelProperty("子账户类型:1-押金，2-余额，3-红包")
    private Integer type;
    @ApiModelProperty("是否显示：1-是，2-否")
    private Integer show;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}

