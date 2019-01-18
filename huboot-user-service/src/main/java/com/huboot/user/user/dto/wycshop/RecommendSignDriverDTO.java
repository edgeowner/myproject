package com.huboot.user.user.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *推荐招聘司机
 */
@Data
public class RecommendSignDriverDTO implements Serializable {

	@ApiModelProperty("招聘用户id")
	private Long driverUserId ;
	@ApiModelProperty("招聘用户姓名")
	private String driverName ;
	@ApiModelProperty("招聘用户手机")
	private String driverPhone ;
	@ApiModelProperty("推荐人奖励佣金")
	private BigDecimal brokerage ;
	@ApiModelProperty("签约时间")
	private LocalDateTime signingTime ;
}

