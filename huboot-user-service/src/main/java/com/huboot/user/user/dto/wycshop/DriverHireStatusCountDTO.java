package com.huboot.user.user.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *司机
 */
@Data
public class DriverHireStatusCountDTO implements Serializable {

	@ApiModelProperty("待分配")
	private Integer waitAllocationCount ;
	@ApiModelProperty("待跟进")
	private Integer waitFollowCount ;
	@ApiModelProperty("跟进中")
	private Integer followingCount ;

}

