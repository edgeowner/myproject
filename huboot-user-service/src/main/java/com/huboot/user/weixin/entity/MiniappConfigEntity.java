package com.huboot.user.weixin.entity;

import com.huboot.commons.jpa.AbstractEntity;
import com.huboot.share.user_service.enums.MiniappConfigTypeEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
*小程序配置
*/
@Entity
@Table(name = "us_miniapp_config")
@DynamicInsert
@DynamicUpdate
@Data
public class MiniappConfigEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //miniappId
    private String miniappId ;
    //设置类型
    @Enumerated(EnumType.STRING)
    private MiniappConfigTypeEnum type ;
    //请求参数
    private String requestBody ;

}

