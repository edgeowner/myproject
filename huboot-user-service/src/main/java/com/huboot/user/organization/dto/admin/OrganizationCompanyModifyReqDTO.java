package com.huboot.user.organization.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户服务-公司表
 */
@Data
public class OrganizationCompanyModifyReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "唯一标识", hidden = true)
    private Long id;
    @ApiModelProperty("公司编号")
    @NotNull(message = "请正确填写")
    private String sn;
    @ApiModelProperty("注册号/统一社会信用代码")
    @NotNull(message = "请正确填写")
    private String regNum;
    @ApiModelProperty("公司名称")
    @NotNull(message = "请正确填写")
    private String name;
    @ApiModelProperty("简称")
    @NotNull(message = "请正确填写")
    private String abbreviation;
    @ApiModelProperty("地区")
    @NotNull(message = "请正确填写")
    private Long areaId;
    @ApiModelProperty("地址")
    @NotNull(message = "请正确填写")
    private String address;
    @ApiModelProperty("成立时间")
    @NotNull(message = "请正确填写")
    private LocalDateTime buildTime;
    @ApiModelProperty("法人")
    @NotNull(message = "请正确填写")
    private String person;
    @ApiModelProperty("法人身份证")
    @NotNull(message = "请正确填写")
    private String personIdcard;
    @ApiModelProperty("营业执照路径")
    @NotNull(message = "请正确填写")
    private String businessLicensePath;
    @ApiModelProperty("身份证正面路径")
    @NotNull(message = "请正确填写")
    private String idcardFacePath ;
    @ApiModelProperty("身份证反面路径")
    @NotNull(message = "请正确填写")
    private String idcardBackPath ;

}

