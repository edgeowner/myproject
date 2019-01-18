package com.huboot.user.organization.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 司机福利
 */
@Data
public class OrganizationShopMicropageWelfareDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("富文本内容")
    private String content;

}

