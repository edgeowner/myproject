package com.huboot.user.weixin.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *小程序
 */
@Data
public class MiniappPagerDTO implements Serializable {

	@ApiModelProperty("公司店铺名称")
	private String shopName ;
	@ApiModelProperty("miniapp_id")
	private String miniappId ;
	@ApiModelProperty("授权状态")
	private String status ;
	@ApiModelProperty("昵称")
	private String nickName ;
	@ApiModelProperty("主体名称")
	private String principalName ;
	@ApiModelProperty("是否发布过")
	private String hasRelease ;
	@ApiModelProperty("是否发布过")
	private String hasReleaseName ;
	@ApiModelProperty("是否可以批量发布")
	private String canBitchRelease ;
	@ApiModelProperty("是否可以批量发布")
	private String canBitchReleaseName ;

}

