package com.huboot.user.user.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *用户服务-用户角色关系表
 */
@Data
public class UserRoleRelationDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("用户ID")
	private Long userId ;
	@ApiModelProperty("角色ID")
	private Long roleId ;

}

