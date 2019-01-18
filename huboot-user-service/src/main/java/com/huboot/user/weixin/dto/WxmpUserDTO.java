package com.huboot.user.weixin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *小程序微信用户
 */
@Data
public class WxmpUserDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("wxmpapp_id")
	private String wxmpappId ;
	@ApiModelProperty("open_id")
	private String openId ;
	@ApiModelProperty("union_id")
	private String unionId ;
	@ApiModelProperty("昵称")
	private String nickname ;
	@ApiModelProperty("头像")
	private String headImgUrl ;
	@ApiModelProperty("是否关注")
	private Integer subscribe ;
	@ApiModelProperty("是否历史关注过")
	private Integer hisSubscribe ;
	@ApiModelProperty("场景码")
	private String qrScene ;

}

