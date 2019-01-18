package com.huboot.user.weixin.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *微信公众号菜单
 */
@Data
public class WxmpMenuTempalteDTO implements Serializable {

	private List<WxmpMenuTempalte> menuTempalteList = new ArrayList<>();

	@Data
	public static class WxmpMenuTempalte {
		@ApiModelProperty("关联类型")
		private String type ;
		@ApiModelProperty("菜单标题")
		private String name ;
		@ApiModelProperty("显示名称")
		private String displayname ;
		@ApiModelProperty("网页链接")
		private String url ;
	}



}

