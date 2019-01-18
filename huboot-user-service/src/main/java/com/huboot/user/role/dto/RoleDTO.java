package com.huboot.user.role.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *用户服务-角色表
 */
@Data
public class RoleDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("名称")
	private String name ;
	@ApiModelProperty("编码ROLE_")
	private String code ;
	@ApiModelProperty("角色描述")
	private String description ;
	@ApiModelProperty("组织ID-公司根节点")
	private Long organizationId ;

}

