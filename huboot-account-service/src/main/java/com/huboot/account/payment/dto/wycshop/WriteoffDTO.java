package com.huboot.account.payment.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *支付订单表
 */
@Data
public class WriteoffDTO implements Serializable {

	@ApiModelProperty("核销主账户关联id")
	@NotBlank(message = "核销主账户关联id不能为空")
	private String userId ;

	@ApiModelProperty("核销金额，单位精确到分")
	@NotNull(message = "核销金额不能为空")
	private BigDecimal amount ;

	@ApiModelProperty("核销流水号")
	@NotBlank(message = "核销流水号不能为空")
	private String offlineSeq ;

	@ApiModelProperty("备注")
	private String remark ;

}

