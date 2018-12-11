package com.huboot.business.base_model.weixin_service.entity;

import java.io.Serializable;

import com.huboot.business.common.jpa.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

/**
*商家微信公众号配置信息表
*/
@Entity
@Table(name = "xiehua_weixin_public_setting")
@DynamicInsert
@DynamicUpdate
@Data
public class WeixinPublicSettingEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    //微信公众号审核通过后生成的唯一标识,对外暴露
    private String weixinUid ;
    //设置类型
    private SetTypeEnum setType ;
    //设置参数
    private String setParameter ;
    //状态
    private StatusEnum status ;
    //设置结果
    private String setResult ;

    public enum SetTypeEnum {
        none,
        serverDomainName,//服务器域名1
        businessDomainName,//业务域名2
        weappSupportVersion;//版本库3

        public static SetTypeEnum get(Integer value) {
            for(SetTypeEnum typeEnum : SetTypeEnum.values()) {
                if(typeEnum.ordinal() == value) {
                    return typeEnum;
                }
            }
            return none;
        }
    }


    public enum StatusEnum {
        none,
        domainSetSuccess,//1
        domainSetFailure,//2
    }




}

