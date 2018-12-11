package com.huboot.business.base_model.weixin_service.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *微信用户关注公众号关系表
 */
@Data
public class WeixinUserPublicMapDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Integer id ;
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime ;
	@ApiModelProperty("微信openId")
	private String openId ;
	@ApiModelProperty("微信公众号审核通过后生成的唯一标识,对外暴露")
	private String weixinUid ;
	@ApiModelProperty("只有在用户将公众号绑定到微信开放平台帐号后")
	private String unionid ;

}

