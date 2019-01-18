package com.huboot.user.user.dto.wycshop;

import com.huboot.commons.page.AbstractQueryReqDTO;
import com.huboot.share.user_service.enums.RecommenderDescEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *推荐人
 */
@Data
public class RecommenderQueryReqDTO extends AbstractQueryReqDTO implements Serializable {

	/*@ApiModelProperty("唯一标识")
	private Long id ;*/
	@ApiModelProperty("姓名")
	private String name ;
	@ApiModelProperty("手机")
	private String phone ;
	@ApiModelProperty("角色描述")
	private RecommenderDescEnum description ;
//	oneLevelSource
	@ApiModelProperty("注册渠道编号")
	private String secondLevelSource;
}

