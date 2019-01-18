package com.huboot.user.user.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnumRequestDTO {
    @ApiModelProperty("枚举值")
    private String value;
    @ApiModelProperty("显示名称")
    private String showName;
}
