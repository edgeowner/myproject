package com.huboot.business.base_model.ali_service.entity;

import com.huboot.business.common.jpa.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
*短信模板-
*/
@Entity
@Table(name = "zb_system_sms_template")
@DynamicInsert
@DynamicUpdate
@Data
public class SystemSMSTemplateEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    //短信节点
    private Integer node ;
    //模板id
    private String templateId ;
    //模板内容
    private String template ;
    //系统
    private String system ;
    //状态:0禁,1,启用
    private SystemSMSTemplateEnum status;
    //店铺标识
    private String shopUid;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public enum SystemSMSTemplateEnum {

        disable(0,"禁用"),enable(1,"启用");

        private Integer value;

        private String showName;

    }

}

