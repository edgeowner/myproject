package com.huboot.share.account_service.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CreateOrderPaymentRespDTO implements Serializable {

    @ApiModelProperty("支付单id")
    private Long paymentOrderId;

    @ApiModelProperty("支付单编号")
    private String paymentOrderSn;

}
