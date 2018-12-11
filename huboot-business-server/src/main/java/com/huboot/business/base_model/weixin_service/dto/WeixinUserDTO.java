package com.huboot.business.base_model.weixin_service.dto;

import java.io.Serializable;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

/**
 *微信用户表
 */
@Data
public class WeixinUserDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Integer id ;
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime ;
	//
	private String openId;
	@ApiModelProperty("用户昵称")
	private String nickname ;
	@ApiModelProperty("用户的性别，值为1时是男性，值为2时是女性，值为0时是未知")
	private Integer sex ;
	@ApiModelProperty("用户个人资料填写的省份")
	private String province ;
	@ApiModelProperty("普通用户个人资料填写的城市")
	private String city ;
	@ApiModelProperty("国家，如中国为CN")
	private String country ;
	@ApiModelProperty("用户头像")
	private String headimgurl ;
	//关注:0取消关注,1关注
	private Integer subscribe ;
	//是否历史关注过
	private Integer hisSubscribe ;

}

