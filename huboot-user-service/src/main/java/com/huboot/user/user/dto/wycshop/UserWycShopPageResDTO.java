package com.huboot.user.user.dto.wycshop;

import com.huboot.share.user_service.enums.UserEmployeeStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户服务-用户基础信息表
 */
@Data
public class UserWycShopPageResDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("唯一标识")
    private Long id;
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty("创建人")
    private String createName;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("用户状态-枚举：enabled(\"使用中\"),disable(\"已禁用\")")
    private UserEmployeeStatusEnum userEmployeeStatus;
    @ApiModelProperty("用户状态-枚举:enabled(\"使用中\"),disable(\"已禁用\")")
    private String userEmployeeStatusName;
    @ApiModelProperty("名称(非实名认证)")
    private String name;
    @ApiModelProperty("角色ID")
    private Long roleId;
    @ApiModelProperty("角色")
    private String roleName;
    @ApiModelProperty("工号")
    private String jobNumber;
}

