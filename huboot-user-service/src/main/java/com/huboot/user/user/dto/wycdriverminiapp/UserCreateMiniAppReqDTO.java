package com.huboot.user.user.dto.wycdriverminiapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户服务-用户基础信息表
 */
@Data
public class UserCreateMiniAppReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("组织id")
    private Long organizationId;
    @ApiModelProperty("openId")
    private String openId;
    @ApiModelProperty("appId")
    private String appId;
}

