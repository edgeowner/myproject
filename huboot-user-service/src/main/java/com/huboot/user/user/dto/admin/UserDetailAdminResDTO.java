package com.huboot.user.user.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户服务-用户基础信息表
 */
@Data
public class UserDetailAdminResDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("唯一标识")
    private Long id;
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty("更新时间")
    private LocalDateTime modifyTime;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("名称(非实名认证)")
    private String name;

}

