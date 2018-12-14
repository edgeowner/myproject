package com.huboot.business.base_model.pay.vo.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huboot.business.base_model.pay.CustomBigDecimalSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

//@ApiModel(value = "账户中心-子账户明细", description = "账户中心-子账户明细")
public class SubRedPacketAccountDetailAdminSumVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("发放红包总额")
    @JsonSerialize(using = CustomBigDecimalSerialize.class)
    private BigDecimal sendAmount;

    @ApiModelProperty("已使用红包总额")
    @JsonSerialize(using = CustomBigDecimalSerialize.class)
    private BigDecimal usedAmount;

    @ApiModelProperty("已失效红包总额")
    @JsonSerialize(using = CustomBigDecimalSerialize.class)
    private BigDecimal expireAmount;

    @ApiModelProperty("未使用红包总额")
    @JsonSerialize(using = CustomBigDecimalSerialize.class)
    private BigDecimal useAmount;

    public BigDecimal getSendAmount() {
        return sendAmount;
    }

    public void setSendAmount(BigDecimal sendAmount) {
        this.sendAmount = sendAmount;
    }

    public BigDecimal getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(BigDecimal usedAmount) {
        this.usedAmount = usedAmount;
    }

    public BigDecimal getExpireAmount() {
        return expireAmount;
    }

    public void setExpireAmount(BigDecimal expireAmount) {
        this.expireAmount = expireAmount;
    }

    public BigDecimal getUseAmount() {
        return useAmount;
    }

    public void setUseAmount(BigDecimal useAmount) {
        this.useAmount = useAmount;
    }
}

