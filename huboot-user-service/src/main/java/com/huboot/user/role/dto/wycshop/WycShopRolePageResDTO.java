package com.huboot.user.role.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户服务-角色表
 */
@Data
public class WycShopRolePageResDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("唯一标识")
    private Long id;
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty("更新人")
    private String modifyName;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("角色描述")
    private String description;

}

