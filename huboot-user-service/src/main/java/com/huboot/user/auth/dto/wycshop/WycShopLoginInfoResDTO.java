package com.huboot.user.auth.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WycShopLoginInfoResDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("唯一标识")
    private Long userId ;
    @ApiModelProperty("用户名")
    private String username ;
    @ApiModelProperty("手机号")
    private String phone ;
    @ApiModelProperty("名称(非实名认证)")
    private String name ;
    @ApiModelProperty("公司名称")
    private String OrganizationCompanyName ;
    @ApiModelProperty("菜单数据：前端控制")
    private List<String> permission;

}
