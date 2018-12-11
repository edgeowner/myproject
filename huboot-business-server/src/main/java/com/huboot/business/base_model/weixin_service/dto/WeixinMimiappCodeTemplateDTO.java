package com.huboot.business.base_model.weixin_service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
*小程序代码模板信息表
*/
@Data
public class WeixinMimiappCodeTemplateDTO implements Serializable {

    private Integer id;
    //代码模板id
    private Integer templateId ;
    //第三方自定义的配置
    private String extJson ;
    //代码版本号，开发者可自定义
    private String userVersion ;
    //代码描述，开发者可自定义
    private String userDesc ;
    //代码包提交审核配置
    private String checkList;

}

