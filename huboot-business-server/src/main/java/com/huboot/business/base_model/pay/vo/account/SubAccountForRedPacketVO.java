package com.huboot.business.base_model.pay.vo.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huboot.business.base_model.pay.CustomBigDecimalSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//@ApiModel(value = "账户中心-子账户", description = "账户中心-子账户")
public class SubAccountForRedPacketVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("子账户余额")
    @JsonSerialize(using = CustomBigDecimalSerialize.class)
    private BigDecimal balance;
    @ApiModelProperty("子账户类型")
    private Integer type;
    @ApiModelProperty("账户状态:1-无效；2-有效")
    private Integer status;
    @ApiModelProperty("账户状态值")
    private String statusName;
    @ApiModelProperty("红包有效期")
    private Date expireDate ;

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

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}

