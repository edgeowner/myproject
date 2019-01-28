package com.huboot.account.account.dto.wycminiapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 */
@Data
public class SubAccountBillDetailDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("三方流水号")
	private String tripartiteSeq ;
	@ApiModelProperty("类型")
	private String orderSource ;
	@ApiModelProperty("类型")
	private String orderSourceName ;
	@ApiModelProperty("变动前金额")
	private String preAmount ;
	@ApiModelProperty("变动金额")
	private String amount ;
	@ApiModelProperty("变动后金额")
	private String afterAmount ;
	@ApiModelProperty("备注")
	private String remark ;
	@ApiModelProperty("操作时间")
	private String operateTime ;
	@ApiModelProperty("符号")
	private String sign ;
	@ApiModelProperty("支付编号")
	private String paymentSn ;
}

