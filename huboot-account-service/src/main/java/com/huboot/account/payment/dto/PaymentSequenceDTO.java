package com.huboot.account.payment.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 *支付流水表
 */
@Data
public class PaymentSequenceDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("支付单or退款单id")
	private Long orderId ;
	@ApiModelProperty("应付金额")
	private BigDecimal payableAmount ;
	@ApiModelProperty("实际支付金额")
	private BigDecimal actualPayAmount ;
	@ApiModelProperty("优惠金额")
	private BigDecimal discountAmount ;
	@ApiModelProperty("支付状态")
	private String payStatus ;
	@ApiModelProperty("成功支付时间")
	private LocalDateTime payTime ;
	@ApiModelProperty("支付平台")
	private String payPlatform ;
	@ApiModelProperty("支付方式")
	private String payType ;
	@ApiModelProperty("在线支付三方请求")
	private String tripartiteRequest ;
	@ApiModelProperty("在线支付三方流水id")
	private String tripartiteId ;
	@ApiModelProperty("在线支付三方相应")
	private String tripartiteResponse ;
	@ApiModelProperty("三方流水号")
	private String tripartiteSeq ;
	@ApiModelProperty("备注")
	private String remark ;

}

