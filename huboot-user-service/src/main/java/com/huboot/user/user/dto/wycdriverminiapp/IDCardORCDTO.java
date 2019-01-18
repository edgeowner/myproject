package com.huboot.user.user.dto.wycdriverminiapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/12 0012.
 */
@Data
public class IDCardORCDTO {

    @ApiModelProperty("姓名")
    private String name ;

    @ApiModelProperty("身份证")
    private String num ;

    @ApiModelProperty("路径")
    private String idcardFacePath ;
}
