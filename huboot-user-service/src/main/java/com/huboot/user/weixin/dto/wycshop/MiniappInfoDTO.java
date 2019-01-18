package com.huboot.user.weixin.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *小程序
 */
@Data
public class MiniappInfoDTO implements Serializable {

	@ApiModelProperty("是否授权")
	private Integer hasAuth ;
	@ApiModelProperty("昵称")
	private String nickName ;
	@ApiModelProperty("头像")
	private String headImg ;
	@ApiModelProperty("公众号的主体名称")
	private String principalName ;
	@ApiModelProperty("账号介绍")
	private String signature ;

}

