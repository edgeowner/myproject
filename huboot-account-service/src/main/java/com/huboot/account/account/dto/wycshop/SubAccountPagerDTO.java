package com.huboot.account.account.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *子账户
 */
@Data
public class SubAccountPagerDTO implements Serializable {

	@ApiModelProperty("用户id")
	private String userId ;
	@ApiModelProperty("姓名")
	private String name ;
	@ApiModelProperty("手机")
	private String phone ;
	@ApiModelProperty("身份证")
	private String idcard ;
	@ApiModelProperty("总余额")
	private String totalBalance ;
	@ApiModelProperty("更新时间)")
	private String updateTime ;

}

