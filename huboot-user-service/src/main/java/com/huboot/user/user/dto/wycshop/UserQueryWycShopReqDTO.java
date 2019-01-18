package com.huboot.user.user.dto.wycshop;

import com.huboot.commons.page.AbstractQueryReqDTO;
import com.huboot.share.user_service.enums.UserEmployeeStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户服务-用户基础信息表
 */
@Data
public class UserQueryWycShopReqDTO extends AbstractQueryReqDTO {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("用户名：账号")
    private String username;
    @ApiModelProperty("工号")
    private String jobNumber;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("用户状态-枚举:enabled(\"使用中\"),disable(\"已禁用\")")
    private UserEmployeeStatusEnum userEmployeeStatus;
    @ApiModelProperty("名称(非实名认证)")
    private String name;
    @ApiModelProperty("角色ID：来源与角色模块的findAll接口")
    private Long roleId;
}

