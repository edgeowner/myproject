package com.huboot.share.account_service.api.dto.order_payment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OPItemDetailsRespDTO implements Serializable {

    @ApiModelProperty("支付or退款订单id")
    private Long orderId ;

    @ApiModelProperty("商品交易订单号")
    private String tradeOrderSn ;

    @ApiModelProperty("订单金额")
    private BigDecimal tradeOrderAmount ;

    @ApiModelProperty("交易订单描述")
    private String tradeOrderDesc ;

    @ApiModelProperty("交易订单类型")
    private String tradeOrderType ;
}
