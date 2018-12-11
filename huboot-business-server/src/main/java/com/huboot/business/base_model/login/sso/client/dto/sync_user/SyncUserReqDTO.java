package com.huboot.business.base_model.login.sso.client.dto.sync_user;

import com.huboot.business.base_model.login.sso.client.dto.BaseUser;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 同步用户数据ReqDTO
 * **/
@Data
public class SyncUserReqDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "加密类不能为空")
    private String encrypeClass;

    @NotBlank(message = "账号不能为空")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "系统来源不能为空")
    private String fromSystem;

    @NotBlank(message = "加密类不能为空")
    private BaseUser.UserStatus status;

    private LocalDateTime createTime;

}
