package com.huboot.business.base_model.weixin_service.dto.weixin_center.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 *商家微信公众号配置信息表
 */
@Data
public class WeixinPublicTicketDTO implements Serializable {

	@ApiModelProperty("当前页面的url-全路径")
	@NotEmpty(message = "url不能为空")
	private String url ;

}

