package com.huboot.business.base_model.weixin_service.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *微信公众号菜单
 */
@Data
public class WeixinPublicMenuDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Integer id ;
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime ;
	@ApiModelProperty("微信公众号审核通过后生成的唯一标识,对外暴露")
	private String weixinUid ;
	@ApiModelProperty("微信菜单级别（一级菜单数组，个数应为1~3个,二级菜单数组，个数应为1~5个），code=WECHAT_MENU_LEVEL")
	private Integer level ;
	@ApiModelProperty("菜单标题，不超过16个字节，子菜单不超过60个字节")
	private String name ;
	@ApiModelProperty("微信菜单类型（菜单的响应动作类型）,code=WECHAT_MENU_TYPE")
	private Integer type ;
	@ApiModelProperty("菜单KEY值，用于消息接口推送，不超过128字节（click等点击类型必须）")
	private String key ;
	@ApiModelProperty("网页链接，用户点击菜单可打开链接，不超过1024字节（view类型必须）")
	private String url ;
	@ApiModelProperty("创建人")
	private Long parentId ;

}

