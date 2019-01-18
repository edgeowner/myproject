package com.huboot.user.user.dto.wycshop;

import com.huboot.share.user_service.enums.DriverHireFollowStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *推荐招聘司机
 */
@Data
public class RecommendListInfoDTO implements Serializable {

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
	@ApiModelProperty("注册时间")
	private LocalDateTime registerTime;
	@ApiModelProperty("合同号")
	private String contractCode;
	@ApiModelProperty("进度枚举值")
	private DriverHireFollowStatusEnum scheduleEnum;
	@ApiModelProperty("进度")
	private String schedule;
}

