package com.huboot.user.organization.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 公司介绍
 */
@Data
public class OrganizationShopMicropageIntroductionDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("富文本内容")
    private String content;

}

