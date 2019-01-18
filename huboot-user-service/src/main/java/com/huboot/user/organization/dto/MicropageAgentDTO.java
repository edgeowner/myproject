package com.huboot.user.organization.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 经纪人推广
 */
@Data
public class MicropageAgentDTO implements Serializable {
    @ApiModelProperty("推广者宣传图")
    private String imagePath;
    @ApiModelProperty("推广者活动细则")
    private String content;
    @ApiModelProperty("司机宣传图")
    private String driverImagePath;
    @ApiModelProperty("公司名称")
    private String company;
    @ApiModelProperty("公司联系人")
    private String contact;
    @ApiModelProperty("公司联系方式")
    private String phone;
    @ApiModelProperty("公司联系地址")
    private String address;
}

