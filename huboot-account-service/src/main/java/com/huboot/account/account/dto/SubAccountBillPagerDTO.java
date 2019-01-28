package com.huboot.account.account.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 */
@Data
public class SubAccountBillPagerDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("类型")
	private String orderSource ;
	@ApiModelProperty("类型")
	private String orderSourceName ;
	@ApiModelProperty("账单状态")
	private String status ;
	@ApiModelProperty("账单状态名称")
	private String statusName ;
	@ApiModelProperty("符号")
	private String sign ;
	@ApiModelProperty("账户原金额")
	private String preAmount ;
	@ApiModelProperty("变动金额")
	private String amount ;
	@ApiModelProperty("变动后金额")
	private String afterAmount ;
	@ApiModelProperty("备注")
	private String remark ;
	@ApiModelProperty("操作日期")
	private String operateDate ;
	@ApiModelProperty("操作时间")
	private String operateTime ;

}

