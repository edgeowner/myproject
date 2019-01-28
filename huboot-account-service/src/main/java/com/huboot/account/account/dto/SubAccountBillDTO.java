package com.huboot.account.account.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 *
 */
@Data
public class SubAccountBillDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("主账户id")
	private Long accountId;
	@ApiModelProperty("子账户id")
	private Long subAccountId ;
	@ApiModelProperty("账单状态")
	private String status ;
	@ApiModelProperty("账单分类")
	private String type ;
	@ApiModelProperty("符号")
	private String sign ;
	@ApiModelProperty("账户原金额")
	private BigDecimal preAmount ;
	@ApiModelProperty("变动金额")
	private BigDecimal amount ;
	@ApiModelProperty("变动后金额")
	private BigDecimal afterAmount ;
	@ApiModelProperty("订单id")
	private Long orderId ;
	@ApiModelProperty("支付流水id")
	private Long paymentSeqId ;
	@ApiModelProperty("备注")
	private String remark ;

}

