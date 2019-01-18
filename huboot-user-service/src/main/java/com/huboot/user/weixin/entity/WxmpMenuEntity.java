package com.huboot.user.weixin.entity;

import com.huboot.commons.jpa.AbstractEntity;
import com.huboot.share.user_service.enums.WxmpMenuLevelEnum;
import com.huboot.share.user_service.enums.WxmpMenuTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
*微信公众号菜单
* 防止无限递归：StackOverflowError
*  https://blog.csdn.net/li_xiang_lin/article/details/78738147
*  https://blog.csdn.net/code_du/article/details/37809567
*  https://blog.csdn.net/a499477783/article/details/79969750
*/
@Entity
@Table(name = "us_wxmp_menu")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class WxmpMenuEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //公众号appid
    private String wxmpappId ;
    //微信菜单级别（一级菜单数组，个数应为1~3个,二级菜单数组，个数应为1~5个），code=WECHAT_MENU_LEVEL
    @Enumerated(EnumType.ORDINAL)
    private WxmpMenuLevelEnum level ;
    //菜单标题，不超过16个字节，子菜单不超过60个字节
    private String name ;
    //微信菜单类型（菜单的响应动作类型）
    @Enumerated(EnumType.STRING)
    private WxmpMenuTypeEnum type ;
    //菜单KEY值，用于消息接口推送，不超过128字节（click等点击类型必须）
    private String key ;
    //网页链接，用户点击菜单可打开链接，不超过1024字节（view类型必须）
    private String url ;
    //父级菜单id
    private Long parentId ;
    //是否需要微信授权认证url
    private Integer needAuth ;
    //顺序
    private Integer sequence ;

}

