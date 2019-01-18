package com.huboot.user.organization.dto.admin;

import java.io.Serializable;

import com.huboot.commons.page.AbstractQueryReqDTO;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 用户服务-公司表
 */
@Data
public class OrganizationCompanyQueryReqDTO extends AbstractQueryReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("公司名称")
    private String name;
    @ApiModelProperty("简称")
    private String abbreviation;
    @ApiModelProperty("公司代码")
    private String code;
    @ApiModelProperty("地区")
    private Long areaId;

}

