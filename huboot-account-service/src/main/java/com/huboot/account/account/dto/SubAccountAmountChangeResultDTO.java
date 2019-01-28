package com.huboot.account.account.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *子账户
 */
@Data
public class SubAccountAmountChangeResultDTO implements Serializable {

	@ApiModelProperty("变更前余额")
	private BigDecimal preAmount ;
	@ApiModelProperty("变更金额")
	private BigDecimal amount ;
	@ApiModelProperty("变更后余额")
	private BigDecimal afterAmount ;

}

