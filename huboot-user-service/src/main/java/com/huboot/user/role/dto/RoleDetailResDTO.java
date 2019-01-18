package com.huboot.user.role.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户服务-角色表
 */
@Data
public class RoleDetailResDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("唯一标识")
    private Long id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("角色描述")
    private String description;
    @ApiModelProperty("菜单数据：前端控制")
    private List<String> permission;
    @ApiModelProperty("颜色,产品要求:颜色，前端渲染需要")
    private String colour;
}

