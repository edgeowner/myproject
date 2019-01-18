package com.huboot.user.user.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 *推荐招聘司机
 */
@Data
public class RecommendDriverDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("推荐人id")
	private Long recommenderId ;
	@ApiModelProperty("推荐人userid")
	private Long recommenderUserId ;
	@ApiModelProperty("招聘信息id")
	private Long driverHireId ;
	@ApiModelProperty("招聘用户id")
	private Long hireUserId ;
	@ApiModelProperty("推荐人奖励佣金")
	private BigDecimal brokerage ;

}

