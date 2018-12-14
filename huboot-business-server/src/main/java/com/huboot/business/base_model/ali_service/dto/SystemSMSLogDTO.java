package com.huboot.business.base_model.ali_service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *交易中心-
 */
@Data
public class SystemSMSLogDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Integer id ;
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime ;
	@ApiModelProperty("短信节点")
	private Integer node ;
	@ApiModelProperty("手机号")
	private String phone ;
	@ApiModelProperty("发送体")
	private String smsRequest ;
	@ApiModelProperty("响应体")
	private String smsResponse ;
	@ApiModelProperty("所属系统")
	private Integer system ;
}

