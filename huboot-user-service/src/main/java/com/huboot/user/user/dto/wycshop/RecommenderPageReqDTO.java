package com.huboot.user.user.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *推荐人
 */
@Data
public class RecommenderPageReqDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("用户ID")
	private Long userId;
	@ApiModelProperty("姓名")
	private String name ;
	@ApiModelProperty("手机")
	private String phone ;
	@ApiModelProperty("角色描述")
	private String description ;
	@ApiModelProperty("累计推广人数")
	private Long counts;
	@ApiModelProperty("累计佣金")
	private String money;
	@ApiModelProperty("渠道来源")
	private String promotionSource;
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime;
	@ApiModelProperty("更新时间")
	private LocalDateTime modifyTime;

}

