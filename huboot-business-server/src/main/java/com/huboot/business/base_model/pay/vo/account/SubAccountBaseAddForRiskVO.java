package com.huboot.business.base_model.pay.vo.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SubAccountBaseAddForRiskVO implements Serializable {

	@ApiModelProperty("用户gid")
	private String userGid ;
	@ApiModelProperty("公司uid")
	private Long shopId ;
	@ApiModelProperty("公司uid")
	private String shopName;


}

