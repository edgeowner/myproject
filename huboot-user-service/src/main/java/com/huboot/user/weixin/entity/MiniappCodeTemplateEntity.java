package com.huboot.user.weixin.entity;

import com.huboot.commons.jpa.AbstractEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
*小程序代码库
*/
@Entity
@Table(name = "us_miniapp_code_template")
@DynamicInsert
@DynamicUpdate
@Data
public class MiniappCodeTemplateEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //代码模板id
    private String templateId ;
    //代码版本号，开发者可自定义
    private String userVersion ;
    //代码描述，开发者可自定义
    private String userDesc ;

}

