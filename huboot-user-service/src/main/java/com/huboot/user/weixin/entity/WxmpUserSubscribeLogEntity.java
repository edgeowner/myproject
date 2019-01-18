package com.huboot.user.weixin.entity;

import com.huboot.commons.jpa.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
*微信用户关注历史信息
* 防止无限递归：StackOverflowError
*  https://blog.csdn.net/li_xiang_lin/article/details/78738147
*  https://blog.csdn.net/code_du/article/details/37809567
*  https://blog.csdn.net/a499477783/article/details/79969750
*/
@Entity
@Table(name = "us_wxmp_user_subscribe_log")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class WxmpUserSubscribeLogEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //wxmpapp_id
    private String wxmpappId ;
    //open_id
    private String openId ;
    //二维码扫码场景
    private String qrScene ;

}

