package com.huboot.user.weixin.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *小程序
 */
@Data
public class MiniappSettingDTO implements Serializable {

	@ApiModelProperty("服务器域名")
	private String serverDomain ;
	@ApiModelProperty("基础库版本")
	private String baseVersion ;

}

