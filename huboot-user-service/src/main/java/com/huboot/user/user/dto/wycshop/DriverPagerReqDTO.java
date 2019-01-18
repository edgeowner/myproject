package com.huboot.user.user.dto.wycshop;

import com.huboot.commons.page.AbstractQueryReqDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *司机
 */
@Data
public class DriverPagerReqDTO extends AbstractQueryReqDTO {

	@ApiModelProperty("用户ID")
	private String userName ;
	@ApiModelProperty("手机")
	private String phone ;
	@ApiModelProperty("身份证")
	private String idCard ;
	@ApiModelProperty("一级渠道")
	private String oneLevelSource ;
	@ApiModelProperty("二级渠道")
	private String secondLevelSource ;

}

