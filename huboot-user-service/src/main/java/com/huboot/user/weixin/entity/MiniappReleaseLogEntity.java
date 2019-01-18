package com.huboot.user.weixin.entity;

import com.huboot.commons.jpa.AbstractEntity;
import com.huboot.share.user_service.enums.MiniappReleaseStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
*小程序发布记录
*/
@Entity
@Table(name = "us_miniapp_release_log")
@DynamicInsert
@DynamicUpdate
@Data
public class MiniappReleaseLogEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //nimiapp_id
    private String miniappId ;
    //代码模板id
    private String templateId ;
    //状态
    @Enumerated(EnumType.STRING)
    private MiniappReleaseStatusEnum status ;
    //小程序提交代码扩展配置参数
    private String commitCodeParameter ;
    //提交代码结果
    private String commitCodeResult ;
    //提交审核结果
    private String commitAuditResult ;
    //审核编号
    private String auditId ;
    //审核结果
    private String auditResult ;
    //审核通过之后是否自动发布
    private String releaseAfterAudit ;
    //发布结果
    private String releaseResult ;

}

