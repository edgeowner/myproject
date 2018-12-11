package com.huboot.business.base_model.weixin_service.entity;

import java.io.Serializable;

import com.huboot.business.common.jpa.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

/**
*小程序代码模板信息表
*/
@Entity
@Table(name = "xiehua_weixin_nimiapp_code_template")
@DynamicInsert
@DynamicUpdate
@Data
public class WeixinMimiappCodeTemplateEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
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

