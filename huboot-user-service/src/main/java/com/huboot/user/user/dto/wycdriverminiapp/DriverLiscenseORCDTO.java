package com.huboot.user.user.dto.wycdriverminiapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/12 0012.
 */
@Data
public class DriverLiscenseORCDTO {

    @ApiModelProperty("姓名")
    private String licName = "";

    @ApiModelProperty("身份证")
    private String licNum = "" ;

    @ApiModelProperty("准驾车型")
    private String licCarModel = "" ;

    @ApiModelProperty("初次领证日期")
    private String licGetDate = "" ;

    @ApiModelProperty("有效期限")
    private String licValidity = "" ;

    @ApiModelProperty("路径")
    private String path = "" ;
}
