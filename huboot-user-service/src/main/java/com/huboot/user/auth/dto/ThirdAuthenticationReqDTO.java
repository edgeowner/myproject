package com.huboot.user.auth.dto;

import com.huboot.share.user_service.enums.ThirdPlatformEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ThirdAuthenticationReqDTO implements Serializable {

    private static final long serialVersionUID = -8445943548965154778L;
    @NotEmpty
    private String code;
    @NotNull
    @ApiModelProperty("微信公众平台：miniapp(\"小程序\"), mp(\"公众号\")")
    private ThirdPlatformEnum platform;

}
