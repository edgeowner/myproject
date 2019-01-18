package com.huboot.user.weixin.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 *小程序
 */
@Data
public class OpenAuthBindDTO implements Serializable {

	@NotEmpty(message = "小程序授权code不能为空")
	@ApiModelProperty("小程序授权code")
	private String authorizationCode ;

}

