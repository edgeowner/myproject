package com.huboot.user.user.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *司机招聘跟进信息
 */
@Data
public class DriverFollowLogInfoDTO implements Serializable {

	@ApiModelProperty("跟进用户id")
	private Long followerId ;
	@ApiModelProperty("跟进人")
	private String followerName ;
	@ApiModelProperty("意向")
	private String intention ;
	@ApiModelProperty("跟进方式")
	private String type ;
	@ApiModelProperty("联系结果")
	private String contactResult ;
	@ApiModelProperty("跟进时间")
	private String createTime ;
	@ApiModelProperty("反馈")
	private String feedback ;

}

