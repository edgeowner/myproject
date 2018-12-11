package com.huboot.business.base_model.weixin_service.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *商家微信公众号配置信息表
 */
@Data
public class WeixinPublicSettingQueryDTO implements Serializable {

	@ApiModelProperty("创建时间")
	private LocalDateTime createTime ;
	@ApiModelProperty("微信公众号审核通过后生成的唯一标识,对外暴露")
	private String weixinUid ;
	@ApiModelProperty("设置类型")
	private Integer setType ;
	@ApiModelProperty("设置参数")
	private String setParameter ;
	@ApiModelProperty("状态")
	private Integer status ;
	@ApiModelProperty("设置结果")
	private String setResult ;
	@ApiModelProperty("页码")
	private Integer page = 0;
	@ApiModelProperty("条数")
	private Integer size = 10;

}

