package com.huboot.user.user.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户服务-用户基础信息表
 */
@Data
public class UserCreateAdminReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("姓名")
    @NotBlank(message = "姓名不能为空")
    private String name;
    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号不能为空")
    private String phone;
    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}

