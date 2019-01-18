package com.huboot.user.weixin.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *小程序
 */
@Data
public class WxmpPagerDTO implements Serializable {

	@ApiModelProperty("公司店铺名称")
	private String shopName ;
	@ApiModelProperty("公众号appid")
	private String wxmpappId ;
	@ApiModelProperty("状态")
	private String status ;
	@ApiModelProperty("昵称")
	private String nickName ;
	@ApiModelProperty("公众号的主体名称")
	private String principalName ;
	@ApiModelProperty("二维码路径")
	private String qrcodeImg ;

}

