package com.huboot.user.weixin.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 *小程序代码库
 */
@Data
public class MiniappCodeTemplateCreateDTO implements Serializable {

	@NotEmpty(message = "代码模板id不能为空")
	@ApiModelProperty("代码模板id")
	private String templateId ;

	@NotEmpty(message = "代码版本号不能为空")
	@ApiModelProperty("代码版本号，开发者可自定义")
	private String userVersion ;

	@NotEmpty(message = "代码描述不能为空")
	@ApiModelProperty("代码描述，开发者可自定义")
	private String userDesc ;

}

