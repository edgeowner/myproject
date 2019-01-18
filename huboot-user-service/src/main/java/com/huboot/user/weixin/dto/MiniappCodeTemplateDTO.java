package com.huboot.user.weixin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *小程序代码库
 */
@Data
public class MiniappCodeTemplateDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("代码模板id")
	private String templateId ;
	@ApiModelProperty("代码版本号，开发者可自定义")
	private String userVersion ;
	@ApiModelProperty("代码描述，开发者可自定义")
	private String userDesc ;

}

