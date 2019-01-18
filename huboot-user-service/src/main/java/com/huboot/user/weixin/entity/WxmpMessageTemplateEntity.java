package com.huboot.user.weixin.entity;

import com.huboot.commons.jpa.AbstractEntity;
import com.huboot.share.common.enums.YesOrNoEnum;
import com.huboot.share.user_service.enums.WeixinMessageNodeEnum;
import com.huboot.share.user_service.enums.WeixinTypeEnum;
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
@Table(name = "us_wxmp_message_template")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class WxmpMessageTemplateEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //微信通知节点
    @Enumerated(EnumType.STRING)
    private WeixinMessageNodeEnum node ;
    //模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式
    private String templateIdShort ;
    //打开方式（h5或者小程序）
    @Enumerated(EnumType.STRING)
    private WeixinTypeEnum openType ;
    //url
    private String url ;
    //所需跳转到小程序的具体页面路径
    private String miniPagepath ;
    //H5跳转url是否需要认证授权
    @Enumerated(EnumType.STRING)
    private YesOrNoEnum urlNeedAuth ;
    //备注
    private String remark ;

}

