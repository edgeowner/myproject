package com.huboot.user.organization.dto.zkshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户服务-公司表
 */
@Data
public class OrganizationCompanyCreateForZkShopReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("注册号/统一社会信用代码")
    @NotNull(message = "请正确填写")
    private String regNum;
    @ApiModelProperty("公司名称")
    @NotNull(message = "请正确填写")
    private String name;
    @ApiModelProperty("法人姓名")
    @NotNull(message = "请正确填写")
    private String person;
    @ApiModelProperty("法人身份证号")
    @NotNull(message = "请正确填写")
    private String personIdcard;
    @ApiModelProperty("营业执照路径")
    @NotNull(message = "请正确填写")
    private String businessLicensePath;

    @ApiModelProperty("地区")
    @NotNull(message = "请正确填写")
    private Long areaId;
    @ApiModelProperty("地址")
    @NotNull(message = "请正确填写")
    private String address;
}

