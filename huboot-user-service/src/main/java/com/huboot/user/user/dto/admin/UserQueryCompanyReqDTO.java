package com.huboot.user.user.dto.admin;

import com.huboot.commons.page.AbstractQueryReqDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户服务-用户基础信息表
 */
@Data
public class UserQueryCompanyReqDTO extends AbstractQueryReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("公司名称")
    private Long organizationName ;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("名称(非实名认证)")
    private String name;

}

