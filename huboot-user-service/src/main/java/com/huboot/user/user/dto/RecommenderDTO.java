package com.huboot.user.user.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *推荐人
 */
@Data
public class RecommenderDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("用户ID")
	private Long userId ;
	@ApiModelProperty("组织id")
	private Long organizationId ;
	@ApiModelProperty("角色描述")
	private String description ;

}

