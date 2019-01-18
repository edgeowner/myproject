package com.huboot.user.weixin.entity;

import com.huboot.commons.jpa.AbstractEntity;
import com.huboot.share.user_service.enums.WeixinAuthStatusEnum;
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
@Table(name = "us_wxmp")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class WxmpEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //公众号appid
    private String wxmpappId ;
    //原始id
    private String originalId;
    //
    private String refreshToken;
    //授权状态
    @Enumerated(EnumType.STRING)
    private WeixinAuthStatusEnum status;
    //昵称
    private String nickName ;
    //头像
    private String headImg ;
    //公众号的主体名称
    private String principalName ;
    //授权方公众号所设置的微信号，可能为空
    private String alias ;
    //账号介绍
    private String signature ;
    //二维码路径
    private String qrcodeImg ;
    //关注回复
    private String subscribeReply;

}

