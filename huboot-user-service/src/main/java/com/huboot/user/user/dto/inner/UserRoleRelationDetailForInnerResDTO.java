package com.huboot.user.user.dto.inner;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户服务-用户角色关系表
 */
@Data
public class UserRoleRelationDetailForInnerResDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("唯一标识")
    private Long id;
    @ApiModelProperty("角色")
    private String role;

}

