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
public class RolePageResDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime ;
	@ApiModelProperty("更新时间")
	private LocalDateTime modifyTime ;
	@ApiModelProperty("名称")
	private String name ;
	@ApiModelProperty("编码ROLE_")
	private String code ;
	@ApiModelProperty("角色描述")
	private String description ;
	@ApiModelProperty("组织ID-公司根节点")
	private Long organizationId ;

}

