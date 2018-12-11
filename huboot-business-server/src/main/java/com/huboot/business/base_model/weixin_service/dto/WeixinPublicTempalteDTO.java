package com.huboot.business.base_model.weixin_service.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *微信模板信息表
 */
@Data
public class WeixinPublicTempalteDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Integer id ;
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime ;
	@ApiModelProperty("微信公众号审核通过后生成的唯一标识,对外暴露")
	private String weixinUid ;
	@ApiModelProperty("微信节点")
	private Integer node ;
	@ApiModelProperty("公众号模板id")
	private String templateId ;

}

