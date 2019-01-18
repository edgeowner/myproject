package com.huboot.user.organization.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 车型详情
 */
@Data
public class OrganizationShopMicropageModelDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("标题")
    private String model;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("押金")
    private String deposit;
    @ApiModelProperty("租金")
    private String rent;
    @ApiModelProperty("排量")
    private String displacement;
    @ApiModelProperty("油耗")
    private String fuel;
    @ApiModelProperty("图片")
    private List<String> imageList;

}

