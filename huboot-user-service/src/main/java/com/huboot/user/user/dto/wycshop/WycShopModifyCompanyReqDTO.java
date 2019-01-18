package com.huboot.user.user.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户服务-用户基础信息表
 */
@Data
public class WycShopModifyCompanyReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "唯一标识", hidden = true)
    private Long id;
    @ApiModelProperty("姓名")
    @NotNull(message = "姓名不能为空")
    private String name;
    @ApiModelProperty("手机号")
    @NotNull(message = "手机号不能为空")
    private String phone;
    @ApiModelProperty("工号")
    private String jobNumber;
    @ApiModelProperty("角色Id")
    private Long roleId;
}

