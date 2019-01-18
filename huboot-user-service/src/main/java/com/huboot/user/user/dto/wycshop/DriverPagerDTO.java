package com.huboot.user.user.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *司机
 */
@Data
public class DriverPagerDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long driverId ;
	@ApiModelProperty("用户ID")
	private String userName ;
	@ApiModelProperty("手机")
	private String phone ;
	@ApiModelProperty("身份证")
	private String idCard ;
	@ApiModelProperty("状态")
	private String statusName ;
	@ApiModelProperty("合同号")
	private String contractCode ;
	@ApiModelProperty("签约时间")
	private LocalDateTime signingTime ;
	@ApiModelProperty("一级渠道")
	private String oneLevelSource ;
	@ApiModelProperty("一级渠道")
	private String oneLevelSourceName ;
	@ApiModelProperty("二级渠道")
	private String secondLevelSource ;
	@ApiModelProperty("二级渠道名称")
	private String secondLevelSourceName ;

}

