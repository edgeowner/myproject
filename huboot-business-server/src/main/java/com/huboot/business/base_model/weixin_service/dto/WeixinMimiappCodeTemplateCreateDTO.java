package com.huboot.business.base_model.weixin_service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
*小程序代码模板信息表
*/
@Data
public class WeixinMimiappCodeTemplateCreateDTO implements Serializable {

    //代码模板id
    private Integer templateId ;
    //第三方自定义的配置
    private ExtJSONDTO extJson ;
    //代码版本号，开发者可自定义
    private String userVersion ;
    //代码描述，开发者可自定义
    private String userDesc ;
    //代码包提交审核配置
    private Ckeck checkList;

    @Data
    public static class ExtJSONDTO implements Serializable {
        //系统
        private String extAppid = "${extAppid}";
        //代码模板id
        private Map<String, String> ext ;

    }

    @Data
    public static class Ckeck {
        private List<Item> item_list;
    }

    @Data
    public static class Item {
        private String address;
        private String tag;
        private String first_class;
        private String second_class;
        private String first_id;
        private String second_id;
        private String title;
    }

}

