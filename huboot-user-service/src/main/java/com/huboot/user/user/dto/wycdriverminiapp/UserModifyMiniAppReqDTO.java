package com.huboot.user.user.dto.wycdriverminiapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户服务-用户基础信息表
 */
@Data
public class UserModifyMiniAppReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
    private Long userId;
    @ApiModelProperty("openId")
    private String openId;
}

