package com.huboot.user.role.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 *用户服务-角色表
 */
@Data
public class RoleModifyReqDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "唯一标识", hidden = true)
	private Long id;
	@ApiModelProperty("名称")
	private String name;
	@ApiModelProperty("角色描述")
	private String description;
	@ApiModelProperty("菜单数据：前端控制")
	private List<String> permission;

}

