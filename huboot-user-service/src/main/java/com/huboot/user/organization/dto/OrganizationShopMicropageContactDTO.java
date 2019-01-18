package com.huboot.user.organization.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 联系方式
 */
@Data
public class OrganizationShopMicropageContactDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("联系电话")
    private String phone;
    @ApiModelProperty("公司地址")
    private String address;
    @ApiModelProperty("公司名称")
    private String shopName;

}

