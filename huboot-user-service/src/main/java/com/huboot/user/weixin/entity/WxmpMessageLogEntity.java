package com.huboot.user.weixin.entity;

import com.huboot.commons.jpa.AbstractEntity;
import com.huboot.share.common.enums.SuccessOrFailureEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
*公众号
* 防止无限递归：StackOverflowError
*  https://blog.csdn.net/li_xiang_lin/article/details/78738147
*  https://blog.csdn.net/code_du/article/details/37809567
*  https://blog.csdn.net/a499477783/article/details/79969750
*/
@Entity
@Table(name = "us_wxmp_message_log")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class WxmpMessageLogEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //参数
    private String parameter;
    //公众号appid
    private String wxmpappId ;
    //微信用户openid
    private String openId ;
    //消息节点
    private String node ;
    //消息内容
    private String content ;
    //消息相应
    private String response ;
    //发送状态
    @Enumerated(EnumType.STRING)
    private SuccessOrFailureEnum sendStatus ;
    //错误原因
    private String errorReason ;
    //备注
    private String remark ;

}

