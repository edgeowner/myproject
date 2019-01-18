package com.huboot.user.organization.dto.admin;

import java.io.Serializable;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 用户服务-公司表
 */
@Data
public class OrganizationCompanyPageResDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("唯一标识")
    private Long id;
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty("更新人")
    private String modifyName;
    @ApiModelProperty("公司编号")
    private String sn;
    @ApiModelProperty("公司代码")
    private String code;
    @ApiModelProperty("公司名称")
    private String name;
    @ApiModelProperty("简称")
    private String abbreviation;
    @ApiModelProperty("地区")
    private Long areaId;
    @ApiModelProperty("地区")
    private String areaName;

}

