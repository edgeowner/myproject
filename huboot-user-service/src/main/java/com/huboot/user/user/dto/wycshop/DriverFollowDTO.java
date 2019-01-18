package com.huboot.user.user.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *司机招聘跟进信息
 */
@Data
public class DriverFollowDTO implements Serializable {

	@ApiModelProperty("联系结果")
	private String contactResult ;
	@ApiModelProperty("意向")
	private String intention ;
	@ApiModelProperty("跟进方式")
	private String type ;
	@ApiModelProperty("下次跟进时间")
	private String nextTime ;
	@ApiModelProperty("反馈")
	private String feedback ;
	@ApiModelProperty("合同号")
	private String contractCode ;
	@ApiModelProperty("签约时间")
	private String signingTime ;
	@ApiModelProperty("推荐人奖励佣金")
	private BigDecimal brokerage ;
	@ApiModelProperty("备注")
	private String remark ;

}

