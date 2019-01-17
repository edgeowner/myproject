package com.huboot.share.user_service.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/9/7 0007.
 */
@Data
public class UserPermissionDTO implements Serializable {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("用户角色")
    private List<String> roles;

}
