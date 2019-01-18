package com.huboot.user.user.dto.zkshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 直客商家端-用户服务-企业员工表
 */
@Data
public class ZkshopUserEmployeeDetailResDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("唯一标识")
    private Long id;
    @ApiModelProperty("名称(非实名认证)")
    private String name;
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty("角色Id")
    private Long roleId;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("角色列表")
    private List<Role> roleList = new ArrayList<>();
    @Data
    public class Role {
        @ApiModelProperty("唯一标识")
        private Long id;
        @ApiModelProperty("名称")
        private String name;
    }

}

