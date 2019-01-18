package com.huboot.user.user.dto.wycdriverminiapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *推荐招聘司机
 */
@Data
public class RecommendDetailDTO implements Serializable {


	@ApiModelProperty("推荐人数")
	private Integer count = 0;

	@ApiModelProperty("待签约")
	private List<HireUserInfo> waitList = new ArrayList<>();

	@ApiModelProperty("已签约")
	private List<HireUserInfo> signList = new ArrayList<>();

	@ApiModelProperty("未通过")
	private List<HireUserInfo> failList = new ArrayList<>();

	@Data
	public static class HireUserInfo {
		@ApiModelProperty("日期")
		private String name ;
		@ApiModelProperty("日期")
		private String phone ;
		@ApiModelProperty("日期")
		private String date ;
	}
}

