package com.huboot.user.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class UsernameCodeAuthenticationReqDTO implements Serializable {

    private static final long serialVersionUID = -8445943548965154778L;
    @ApiModelProperty("用户名：仅支持手机号")
    @NotBlank(message = "用户名不能为空")
    private String username;
    @ApiModelProperty("短信验证码")
    @NotBlank(message = "短信验证码不能为空")
    private String smsCode;
    @ApiModelProperty("code")
    @NotBlank(message = "code不能为空")
    private String code;

}
