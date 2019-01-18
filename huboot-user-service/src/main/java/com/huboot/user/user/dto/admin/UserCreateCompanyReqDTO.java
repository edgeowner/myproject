package com.huboot.user.user.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户服务-用户基础信息表
 */
@Data
public class UserCreateCompanyReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("组织id")
    private Long organizationId;

}

