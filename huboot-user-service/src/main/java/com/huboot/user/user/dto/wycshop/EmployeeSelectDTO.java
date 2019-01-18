package com.huboot.user.user.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/17 0017.
 */
@Data
public class EmployeeSelectDTO {

    @ApiModelProperty("唯一标识")
    private Long userId ;

    @ApiModelProperty("用户名")
    private String name ;
}
