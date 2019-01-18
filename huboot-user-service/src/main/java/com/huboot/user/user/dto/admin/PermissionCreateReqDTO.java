package com.huboot.user.user.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 用户服务-角色表
 */
@Data
public class PermissionCreateReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("名称")
    @NotNull(message = "名称不能为空")
    private String name;
    @ApiModelProperty("角色描述")
    private String description;
    @ApiModelProperty("菜单数据：前端控制")
    private List<String> permission;
}

