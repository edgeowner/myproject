package com.huboot.business.base_model.weixin_service.dto.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 *商家微信公众号配置信息表
 */
@Data
@ToString
public class WeixinAuthDTO implements Serializable {

	@ApiModelProperty("openId")
	private String openId ;
	@ApiModelProperty("unionid")
	private String  unionid;
	@ApiModelProperty("sessionKey")
	private String  sessionKey;

}

