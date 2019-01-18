package com.huboot.user.weixin.entity;

import com.huboot.commons.jpa.AbstractEntity;
import com.huboot.share.common.enums.YesOrNoEnum;
import com.huboot.share.user_service.enums.WeixinAuthStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
*小程序
*/
@Entity
@Table(name = "us_miniapp")
@DynamicInsert
@DynamicUpdate
@Data
public class MiniappEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //nimiapp_id
    private String miniappId ;
    //refresh_token
    private String refreshToken ;
    //授权状态
    @Enumerated(EnumType.STRING)
    private WeixinAuthStatusEnum status;
    //是否可以批量发布
    @Enumerated(EnumType.STRING)
    private YesOrNoEnum canBitchRelease ;
    //是否发布过
    @Enumerated(EnumType.STRING)
    private YesOrNoEnum hasRelease ;
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
    private String qrcodeImg;

}

