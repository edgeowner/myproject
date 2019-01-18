package com.huboot.user.user.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *招聘司机信息
 */
@Data
public class DriverHireDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("招聘用户id")
	private Long hireUserId ;
	@ApiModelProperty("招聘用户注册时间")
	private LocalDateTime hireUserRegisterTime ;
	@ApiModelProperty("一级渠道")
	private String oneLevelSource ;
	@ApiModelProperty("二级渠道")
	private String secondLevelSource ;
	@ApiModelProperty("二级渠道名称")
	private String secondLevelSourceName ;
	@ApiModelProperty("跟进用户id")
	private Long followUserId ;
	@ApiModelProperty("跟进状态")
	private String followStatus ;

}

