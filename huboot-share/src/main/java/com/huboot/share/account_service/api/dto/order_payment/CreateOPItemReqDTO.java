package com.huboot.share.account_service.api.dto.order_payment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CreateOPItemReqDTO implements Serializable {

    @ApiModelProperty("商品名字")
    @NotBlank(message = "商品名字不能为空")
    private String goodsName;

    @ApiModelProperty(" 商品描述")
    @NotBlank(message = " 商品描述不能为空")
    private String goodsDesc;

    @ApiModelProperty("商品交易订单号")
    @NotBlank(message = "商品交易订单号不能为空")
    private String tradeOrderSn;//商品交易订单号

    @ApiModelProperty("订单金额")
    @NotNull(message = "订单金额不能为空")
    private BigDecimal tradeOrderAmount;//订单金额

    @ApiModelProperty("交易订单描述")
    private String tradeOrderDesc;//交易订单描述

    @ApiModelProperty("交易订单类型")
    private String tradeOrderType;//交易订单类型
}
