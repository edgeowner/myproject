package com.huboot.user.user.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *用户服务-用户基础信息表
 */
@Data
public class UserDetailDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("全局用户ID")
	private Long guid ;
	@ApiModelProperty("用户名")
	private String username ;
	@ApiModelProperty("手机号")
	private String phone ;
	@ApiModelProperty("电子邮箱")
	private String email ;
	@ApiModelProperty("图片地址(头像)")
	private String imagePath ;
	@ApiModelProperty("昵称")
	private String nickName ;
	@ApiModelProperty("名称")
	private String name ;
	@ApiModelProperty("身份证")
	private String idcard ;

}

