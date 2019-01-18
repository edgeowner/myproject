package com.huboot.user.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdatePasswordReqDTO implements Serializable {

    private static final long serialVersionUID = -8445943548965154778L;
    @ApiModelProperty("旧密码")
    private String oldPassword;
    @ApiModelProperty("新密码")
    private String newPassword;

}
