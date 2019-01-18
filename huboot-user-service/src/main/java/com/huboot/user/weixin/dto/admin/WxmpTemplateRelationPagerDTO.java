package com.huboot.user.weixin.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *公众号
 */
@Data
public class WxmpTemplateRelationPagerDTO implements Serializable {

	@ApiModelProperty("公众号appid")
	private String wxmpappId ;
	@ApiModelProperty("公众号名称")
	private String wxmpappName ;
	@ApiModelProperty("微信通知节点")
	private String node ;
	@ApiModelProperty("微信通知节点")
	private String nodeName ;
	@ApiModelProperty("templateId")
	private String templateId ;


}

