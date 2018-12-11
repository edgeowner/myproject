package com.huboot.business.base_model.weixin_service.dto.weixin_center.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *商家微信公众号配置信息表
 */
@Data
public class WeixinBindUserDTO implements Serializable {

	@ApiModelProperty("openId")
	private String openId ;
	@ApiModelProperty("图像url")
	private String  headimgurl;
	@ApiModelProperty("昵称")
	private String nickname;

}

