package com.huboot.user.user.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *推荐招聘司机
 */
@Data
public class RecommendSummarizeDTO implements Serializable {
	@ApiModelProperty("姓名")
	private String name;
	@ApiModelProperty("手机号")
	private String phone;

	@ApiModelProperty("累计推广人数")
	private Long counts = 0l;
	@ApiModelProperty("待分配")
	private Long waitAllocation = 0l;
	@ApiModelProperty("待跟进人数")
	private Long waitFollowCounts = 0l;
	@ApiModelProperty("跟进中人数")
	private Long followingCounts = 0l;
	@ApiModelProperty("已签约人数")
	private Long signingCounts = 0l;
	@ApiModelProperty("未通过人数")
	private Long failCounts = 0l;

	@ApiModelProperty("累计佣金")
	private String totalAmount;
	@ApiModelProperty("可提佣金")
	private String useableAmount;
	@ApiModelProperty("已提佣金")
	private String usedAmount;
}

