package com.huboot.user.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class UsernamePasswordAuthenticationReqDTO implements Serializable {

    private static final long serialVersionUID = -8445943548965154778L;
    @ApiModelProperty("用户名：仅支持手机号")
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @ApiModelProperty("密码")
    @NotEmpty(message = "密码不能为空")
    private String password;

}
