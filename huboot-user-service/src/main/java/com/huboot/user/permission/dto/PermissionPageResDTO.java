package com.huboot.user.permission.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *用户服务-权限表
 */
@Data
public class PermissionPageResDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime ;
	@ApiModelProperty("更新时间")
	private LocalDateTime modifyTime ;
	@ApiModelProperty("名称")
	private String name ;
	@ApiModelProperty("url，支持Ant")
	private String path ;
	@ApiModelProperty("请求方法-枚举")
	private String method ;

}

