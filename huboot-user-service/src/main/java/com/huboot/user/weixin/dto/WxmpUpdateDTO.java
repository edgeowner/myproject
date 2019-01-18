package com.huboot.user.weixin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *公众号更新实体
 */
@Data
public class WxmpUpdateDTO implements Serializable {

	@ApiModelProperty("关注回复")//
	private String subscribeReply;

}

