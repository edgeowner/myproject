package com.huboot.share.user_service.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *基础服务-系统中心-区域表
 */
@Data
public class AreaDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("地区名")
	private String name ;
	@ApiModelProperty("全称")
	private String fullName ;
	@ApiModelProperty("父地区")
	private Long parentId ;
	@ApiModelProperty("路径")
	private List<Long> path ;
	@ApiModelProperty("地区简称")
	private String shortName ;

}

