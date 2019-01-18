package com.huboot.user.weixin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *小程序微信用户
 */
@Data
public class MiniappUserDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("miniapp_id")
	private String miniappId ;
	@ApiModelProperty("open_id")
	private String openId ;
	@ApiModelProperty("unionId")
	private String unionId;

}

