package com.huboot.user.role.dto.zkshop;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *直客商家端-用户服务-角色表
 */
@Data
public class ZkshopRoleDetailResDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime ;
	@ApiModelProperty("更新时间")
	private LocalDateTime modifyTime ;
	@ApiModelProperty("名称")
	private String name ;
	@ApiModelProperty("组织ID-公司根节点")
	private Long organizationId ;
	@ApiModelProperty("描述")
	private String description ;
	@ApiModelProperty("所属系统-枚举")
	private String system ;

}

