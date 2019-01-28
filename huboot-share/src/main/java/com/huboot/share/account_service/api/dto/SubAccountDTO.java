package com.huboot.share.account_service.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *子账户
 */
@Data
public class SubAccountDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("主账户id")
	private Long accountId ;
	@ApiModelProperty("子账户类型")
	private String type ;
	@ApiModelProperty("子账户状态")
	private String status ;
	@ApiModelProperty("总余额")
	private BigDecimal totalBalance ;
	@ApiModelProperty("可用余额")
	private BigDecimal usableBalance ;
	@ApiModelProperty("不可用余额(冻结)")
	private BigDecimal unusableBalance ;

}

