package com.huboot.user.user.dto;

import java.io.Serializable;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *司机
 */
@Data
public class DriverDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("用户ID")
	private Long userId ;
	@ApiModelProperty("状态")
	private String status ;
	@ApiModelProperty("合同号")
	private String contractCode ;
	@ApiModelProperty("签约时间")
	private LocalDateTime signingTime ;
	@ApiModelProperty("签约组织id")
	private Long signingOrgId ;
	@ApiModelProperty("一级渠道")
	private String oneLevelSource ;
	@ApiModelProperty("二级渠道")
	private String secondLevelSource ;
	@ApiModelProperty("二级渠道名称")
	private String secondLevelSourceName ;
	@ApiModelProperty("招聘信息id")
	private Long driverHireId ;

}

