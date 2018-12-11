package com.huboot.business.base_model.weixin_service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *微信模板信息表
 */
@Data
public class WeixinTempalteAddDTO implements Serializable {

	@ApiModelProperty("微信节点")
	private Integer node ;
	@ApiModelProperty("模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式")
	private String templateIdShort ;
	@ApiModelProperty("标题")
	private String title ;
	@ApiModelProperty("点击模板跳转页面")
	private String clickUrl ;
	@ApiModelProperty("应用系统")
	private Integer system ;
	@ApiModelProperty("是否需要微信授权认证url")
	private Integer needAuth = 0;

}

