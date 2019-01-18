package com.huboot.user.weixin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *微信公众号菜单
 */
@Data
public class WxmpMenuDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("创建时间（不能用于业务）")
	private Timestamp generateTime ;
	@ApiModelProperty("公众号appid")
	private String wxmpappId ;
	@ApiModelProperty("微信菜单级别（一级菜单数组，个数应为1~3个,二级菜单数组，个数应为1~5个），code=WECHAT_MENU_LEVEL")
	private Integer level ;
	@ApiModelProperty("菜单标题，不超过16个字节，子菜单不超过60个字节")
	private String name ;
	@ApiModelProperty("微信菜单类型（菜单的响应动作类型）")
	private String type ;
	@ApiModelProperty("菜单KEY值，用于消息接口推送，不超过128字节（click等点击类型必须）")
	private String key ;
	@ApiModelProperty("网页链接，用户点击菜单可打开链接，不超过1024字节（view类型必须）")
	private String url ;
	@ApiModelProperty("父级菜单id")
	private Integer parentId ;
	@ApiModelProperty("是否需要微信授权认证url")
	private Integer needAuth ;
	@ApiModelProperty("顺序")
	private Integer sequence ;

}

