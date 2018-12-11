package com.huboot.business.base_model.weixin_service.dto.weixin_center.dto;

import com.huboot.business.base_model.weixin_service.dto.common.xenum.SystemEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *商家微信公众号配置信息表
 */
@Data
public class WeixinPublicAuthUrlDTO implements Serializable {

	@ApiModelProperty("路径")
	private String url ;
	@ApiModelProperty("授权方式")
	private String scopeType;
	@ApiModelProperty("系统")
	private SystemEnum system = SystemEnum.zk;

}

