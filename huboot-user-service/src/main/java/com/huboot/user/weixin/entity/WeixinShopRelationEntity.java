package com.huboot.user.weixin.entity;

import com.huboot.commons.jpa.AbstractEntity;
import com.huboot.share.user_service.enums.OpenBindStatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
*小程序
* 防止无限递归：StackOverflowError
*  https://blog.csdn.net/li_xiang_lin/article/details/78738147
*  https://blog.csdn.net/code_du/article/details/37809567
*  https://blog.csdn.net/a499477783/article/details/79969750
*/
@Entity
@Table(name = "us_weixin_shop_relation")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class WeixinShopRelationEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //店铺id
    private Long shopId ;
    //店铺sn
    private String shopSn ;
    //所创建的开放平台帐号的appid
    private String openAppid ;
    //miniapp_id
    private String miniappId ;
    //小程序绑定虚拟开放平台状态
    @Enumerated(EnumType.STRING)
    private OpenBindStatusEnum miniappBindStatus;
    //公众号appid
    private String wxmpId ;
    //公众号绑定虚拟开放平台状态
    @Enumerated(EnumType.STRING)
    private OpenBindStatusEnum wxmpBindStatus;

}

