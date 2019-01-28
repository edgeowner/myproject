package com.huboot.account.payment.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 *支付优惠信息控制表
 */
@Data
public class PaymentDiscountDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("支付订单id")
	private Long paymentOrderId ;
	@ApiModelProperty("优惠类型")
	private String discountType ;
	@ApiModelProperty("子账户id")
	private Long subAccountId ;
	@ApiModelProperty("本次支付最多可使用金额")
	private BigDecimal maxValue ;

}

