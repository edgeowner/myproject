package com.huboot.user.user.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户服务-用户基础信息表
 */
@Data
public class WycShopUserDetailResDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("唯一标识")
    private Long id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("名称(非实名认证)")
    private String name;
    @ApiModelProperty("角色Id")
    private Long roleId;
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty("菜单数据：前端控制")
    private List<String> permission;
    @ApiModelProperty("工号")
    private String jobNumber;
}

