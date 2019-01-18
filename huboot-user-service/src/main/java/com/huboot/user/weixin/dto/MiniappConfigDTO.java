package com.huboot.user.weixin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *小程序配置
 */
@Data
public class MiniappConfigDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("miniapp_id")
	private String miniappId ;
	@ApiModelProperty("设置类型")
	private String type ;
	@ApiModelProperty("请求参数")
	private String requestBody ;

}

