package com.huboot.account.account.dto.wycminiapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *子账户
 */
@Data
public class SubAccountDetailDTO implements Serializable {

	@ApiModelProperty("子账户类型")
	private String type ;
	@ApiModelProperty("子账户状态")
	private String status ;
	@ApiModelProperty("总余额")
	private String totalBalance ;
	@ApiModelProperty("可用余额")
	private String usableBalance ;
	@ApiModelProperty("不可用余额(冻结)")
	private String unusableBalance ;

}

