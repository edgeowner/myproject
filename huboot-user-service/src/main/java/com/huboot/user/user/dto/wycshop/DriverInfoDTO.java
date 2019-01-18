package com.huboot.user.user.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *司机
 */
@Data
public class DriverInfoDTO implements Serializable {

	@ApiModelProperty("合同号")
	private String contractCode ;
	@ApiModelProperty("签约时间")
	private LocalDateTime signingTime ;

}

