package com.huboot.share.account_service.api.dto;

import com.huboot.share.account_service.enums.PayTypeEnum;
import com.huboot.share.account_service.enums.PaymentOrderSourceEnum;
import com.huboot.share.account_service.enums.SubAccountTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *支付订单表
 */
@Data
public class RewardPaymentCreateReqDTO implements Serializable {

	@ApiModelProperty("付钱方主账户关联id")
	@NotBlank(message = "付钱方不能为空")
	private String fromId ;

	@ApiModelProperty("收钱方主账户关联id")
	@NotBlank(message = "收钱方不能为空")
	private String toId ;

	@ApiModelProperty("收钱方子账户类型")
	@NotNull(message = "收钱方子账户类型不能为空")
	private SubAccountTypeEnum toSubAccountType ;

	//
	@ApiModelProperty("奖励来源")
	@NotNull(message = "奖励来源不能为空")
	private PaymentOrderSourceEnum source ;

	//
	@ApiModelProperty("支付方式")
	@NotNull(message = "支付方式不能为空")
	private PayTypeEnum payType ;

	@ApiModelProperty("奖励金额，单位精确到分")
	@NotNull(message = "奖励金额不能为空")
	private BigDecimal amount ;

	@ApiModelProperty("备注")
	private String remark ;

}

