package com.huboot.business.base_model.weixin_service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/25 0025.
 */
@Data
public class WxCodeTemplate {

    @ApiModelProperty("代码模板id")
    private String templateId ;
    @ApiModelProperty("代码版本号，开发者可自定义")
    private String userVersion ;
    @ApiModelProperty("代码描述，开发者可自定义")
    private String userDesc ;
    @ApiModelProperty("是否已添加为系统模板")
    private String hasAdd ;
    @ApiModelProperty("是否已添加为系统模板")
    private String hasAddName ;
}
