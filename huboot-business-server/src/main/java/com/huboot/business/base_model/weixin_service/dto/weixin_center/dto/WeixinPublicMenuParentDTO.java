package com.huboot.business.base_model.weixin_service.dto.weixin_center.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *微信公众号菜单
 */
@Data
public class WeixinPublicMenuParentDTO implements Serializable {

	@ApiModelProperty("菜单标题")
	private String name ;
	@ApiModelProperty("关联类型")
	private String type ;
	@ApiModelProperty("网页链接，")
	private String url ;
	@ApiModelProperty("顺序")
	private Integer order;
	@ApiModelProperty("子菜单")
	private List<WeixinPublicMenuSubDTO> subList;

}

