package com.huboot.user.role.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户服务-角色表
 */
@Data
public class RoleCreateReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("角色描述")
    private String description;
}

