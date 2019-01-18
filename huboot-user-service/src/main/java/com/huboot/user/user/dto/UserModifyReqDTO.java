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
public class UserModifyReqDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "唯一标识", hidden = true)
	private Long id;
	@ApiModelProperty("全局用户ID")
	private Long guid;
	@ApiModelProperty("用户名")
	private String username;
	@ApiModelProperty("密码")
	private String password;
	@ApiModelProperty("手机号")
	private String phone;
	@ApiModelProperty("电子邮箱")
	private String email;
	@ApiModelProperty("性别-枚举")
	private String gender;
	@ApiModelProperty("用户状态-枚举")
	private String userStatus;
	@ApiModelProperty("注册IP")
	private String registerIp;
	@ApiModelProperty("注册来源-枚举")
	private String registerSource;
	@ApiModelProperty("登录失败次数-枚举")
	private String loginFailureCount;
	@ApiModelProperty("最后登录IP")
	private String latestLoginIp;
	@ApiModelProperty("最后登录时间")
	private String latestLoginTime;
	@ApiModelProperty("登陆控制状态-枚举")
	private String loginControlStatus;
	@ApiModelProperty("图片地址(头像)")
	private String imagePath;
	@ApiModelProperty("昵称")
	private String nickName;
	@ApiModelProperty("名称(非实名认证)")
	private String name;

}

