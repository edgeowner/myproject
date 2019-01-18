package com.huboot.user.user.dto.wycdriverminiapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *推荐招聘司机
 */
@Data
public class RecommendIndexDTO implements Serializable {


	@ApiModelProperty("佣金")
	private String brokerage ;

	@ApiModelProperty("推荐人数")
	private Integer count ;

	@ApiModelProperty("二维码")
	private String qrImage ;

	@ApiModelProperty("公司简称")
	private String abbreviation ;

	@ApiModelProperty("渠道吗")
	private String sourceCode ;

}

