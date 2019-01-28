package com.huboot.account.payment.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 *支付订单条目表
 */
@Data
public class PaymentOrderItemDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
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

