package com.huboot.user.user.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 *用户服务-用户基础信息表
 */
@Data
public class UserPageResDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("用户名")
	private String username ;
	@ApiModelProperty("手机号")
	private String phone ;
	@ApiModelProperty("名称(非实名认证)")
	private String name ;

}

