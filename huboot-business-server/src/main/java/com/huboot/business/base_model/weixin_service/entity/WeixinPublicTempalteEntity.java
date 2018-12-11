package com.huboot.business.base_model.weixin_service.entity;

import java.io.Serializable;

import com.huboot.business.common.jpa.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

/**
*微信模板信息表
*/
@Entity
@Table(name = "xiehua_weixin_public_template")
@DynamicInsert
@DynamicUpdate
@Data
public class WeixinPublicTempalteEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    //微信公众号审核通过后生成的唯一标识,对外暴露
    private String weixinUid ;
    //公众号模板id
    private String templateId ;
    //关联模板id
    private Integer relaTemplateId;

}

