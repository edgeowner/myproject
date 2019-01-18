package com.huboot.user.weixin.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *公众号
 */
@Data
public class WxmpMessageLogPagerDTO implements Serializable {

	@ApiModelProperty("id")
	private Long id ;
	@ApiModelProperty("公众号appid")
	private String wxmpappId ;
	@ApiModelProperty("发送时间")
	private LocalDateTime createTime ;
	@ApiModelProperty("微信通知节点")
	private String node ;
	@ApiModelProperty("微信通知节点")
	private String nodeName ;
	@ApiModelProperty("openId")
	private String openId ;
	@ApiModelProperty("发送状态")
	private String sendStatusName ;
	@ApiModelProperty("备注")
	private String remark ;


}

