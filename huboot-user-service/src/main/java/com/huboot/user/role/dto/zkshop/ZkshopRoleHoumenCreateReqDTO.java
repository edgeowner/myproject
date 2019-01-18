package com.huboot.user.role.dto.zkshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 直客商家端-用户服务-角色表
 */
@Data
public class ZkshopRoleHoumenCreateReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("组织ID-公司根节点,如果不传,默认为所有zk商家")
    private Long organizationId;

}

