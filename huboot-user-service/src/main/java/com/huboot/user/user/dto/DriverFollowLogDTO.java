package com.huboot.user.user.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *司机招聘跟进信息
 */
@Data
public class DriverFollowLogDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("招聘id")
	private Long driverFireId ;
	@ApiModelProperty("跟进人id（可能有人离职会前后不一样）")
	private Long followUserId ;
	@ApiModelProperty("意向")
	private String intention ;
	@ApiModelProperty("跟进方式")
	private String type ;
	@ApiModelProperty("联系结果")
	private String contactResult ;
	@ApiModelProperty("下次跟进时间")
	private String nextTime ;
	@ApiModelProperty("反馈")
	private String feedback ;

}

