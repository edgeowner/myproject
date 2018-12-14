package com.huboot.business.base_model.pay.vo.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SubAccountDetailAddForZKRefundItemVO implements Serializable {

    @ApiModelProperty("退款金额")
    private BigDecimal refundAmount;
    @ApiModelProperty("订单类型")
    private Integer orderType;
    @ApiModelProperty("子订单sn")
    private String orderSn ;
}

