package com.huboot.user.weixin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *小程序
 */
@Data
public class MiniappDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("miniapp_id")
	private String miniappId ;
	@ApiModelProperty("refresh_token")
	private String refreshToken ;
	@ApiModelProperty("是否可以发布")
	private String canRelease ;
	@ApiModelProperty("是否发布过")
	private String hasRelease ;
	@ApiModelProperty("昵称")
	private String nickName ;
	@ApiModelProperty("头像")
	private String headImg ;
	@ApiModelProperty("公众号的主体名称")
	private String principalName ;
	@ApiModelProperty("授权方公众号所设置的微信号，可能为空")
	private String alias ;
	@ApiModelProperty("账号介绍")
	private String signature ;

}

