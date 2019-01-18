package com.huboot.user.weixin.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *微信公众号菜单
 */
@Data
public class WxmpMenuSubDTO implements Serializable {

	@ApiModelProperty("菜单标题，不超过16个字节，子菜单不超过60个字节")
	private String name ;
	@ApiModelProperty("关联类型")
	private String type ;
	@ApiModelProperty("网页链接，用户点击菜单可打开链接，不超过1024字节（view类型必须）")
	private String url ;
	@ApiModelProperty("顺序")
	private Integer order;

}

