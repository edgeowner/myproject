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
public class WriteoffPaymentCreateReqDTO implements Serializable {

	@ApiModelProperty("核销主账户关联id")
	@NotBlank(message = "核销主账户关联id不能为空")
	private String relaId ;

	@ApiModelProperty("核销子账户类型")
	@NotNull(message = "核销子账户类型不能为空")
	private SubAccountTypeEnum subAccountType ;

	//
	@ApiModelProperty("核销来源场景")
	@NotNull(message = "核销来源不能为空")
	private PaymentOrderSourceEnum source ;

	//
	@ApiModelProperty("支付方式")
	@NotNull(message = "支付方式不能为空")
	private PayTypeEnum payType ;

	@ApiModelProperty("核销金额，单位精确到分")
	@NotNull(message = "核销金额不能为空")
	private BigDecimal amount ;

	@ApiModelProperty("核销流水号")
	@NotBlank(message = "核销流水号不能为空")
	private String offlineSeq ;

	@ApiModelProperty("备注")
	private String remark ;

}

