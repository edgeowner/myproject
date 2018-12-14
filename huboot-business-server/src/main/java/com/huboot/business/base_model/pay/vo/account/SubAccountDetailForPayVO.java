package com.huboot.business.base_model.pay.vo.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huboot.business.base_model.pay.CustomBigDecimalSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

//@ApiModel(value = "账户中心-子账户明细", description = "账户中心-子账户明细")
public class SubAccountDetailForPayVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("唯一标识")
    private String id;
    @ApiModelProperty("编号")
    private String subAccountDetailSn;
    @ApiModelProperty("业务单号")
    private String tradeSn;
    @ApiModelProperty("主账户id")
    private String accountId;
    @ApiModelProperty("子账户id")
    private String subAccountId;
    @ApiModelProperty("交易金额")
    @JsonSerialize(using = CustomBigDecimalSerialize.class)
    private BigDecimal amountPaid;
    @ApiModelProperty("备注信息")
    private String remark;
    @ApiModelProperty("直客订单类型")
    private Integer zKOrderType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubAccountDetailSn() {
        return subAccountDetailSn;
    }

    public void setSubAccountDetailSn(String subAccountDetailSn) {
        this.subAccountDetailSn = subAccountDetailSn;
    }

    public String getTradeSn() {
        return tradeSn;
    }

    public void setTradeSn(String tradeSn) {
        this.tradeSn = tradeSn;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(String subAccountId) {
        this.subAccountId = subAccountId;
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

    public Integer getzKOrderType() {
        return zKOrderType;
    }

    public void setzKOrderType(Integer zKOrderType) {
        this.zKOrderType = zKOrderType;
    }
}

