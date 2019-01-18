package com.huboot.user.user.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *推荐招聘司机
 */
@Data
public class RecommendInfoDTO implements Serializable {

	@ApiModelProperty("推荐人userid")
	private Long recommenderUserId ;
	@ApiModelProperty("推荐人userid")
	private String recommenderUserName ;
	@ApiModelProperty("推荐人奖励佣金")
	private BigDecimal brokerage ;
	@ApiModelProperty("备注")
	private String remark ;

}

