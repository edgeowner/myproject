package com.huboot.user.user.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *资产服务-人车绑定
 */
@Data
public class UserCarCreateReqDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty("车辆档案id")
	private Long archivesId ;
	@ApiModelProperty("用户手机")
	private Long driverId;

}

