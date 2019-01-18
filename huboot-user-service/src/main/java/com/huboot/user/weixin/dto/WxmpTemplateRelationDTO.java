package com.huboot.user.weixin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *公众号
 */
@Data
public class WxmpTemplateRelationDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("公众号appid")
	private String wxmpappId ;
	@ApiModelProperty("关联消息模板id")
	private Long relaTemplateId ;
	@ApiModelProperty("template_id")
	private String templateId ;

}

