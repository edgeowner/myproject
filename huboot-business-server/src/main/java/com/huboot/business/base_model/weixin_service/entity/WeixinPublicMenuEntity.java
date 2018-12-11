package com.huboot.business.base_model.weixin_service.entity;

import java.io.Serializable;

import com.huboot.business.common.jpa.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

/**
*微信公众号菜单
*/
@Entity
@Table(name = "xiehua_weixin_public_menu")
@DynamicInsert
@DynamicUpdate
@Data
public class WeixinPublicMenuEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    //微信公众号审核通过后生成的唯一标识,对外暴露
    private String weixinUid ;
    //微信菜单级别（一级菜单数组，个数应为1~3个,二级菜单数组，个数应为1~5个）
    private LevelEnum level ;
    //菜单标题，不超过16个字节，子菜单不超过60个字节
    private String name ;
    //微信菜单类型（菜单的响应动作类型）,
    private String type ;
    //菜单KEY值，用于消息接口推送，不超过128字节（click等点击类型必须）
    private String key ;
    //网页链接，用户点击菜单可打开链接，不超过1024字节（view类型必须）
    private String url ;
    //父级菜单id
    private Integer parentId ;
    //是否需要微信授权认证url（1-是，2-否）
    private Integer needAuth;
    //顺序
    private Integer sequence;

    public enum LevelEnum {
        none,
        frist, //一级菜单
        second //二级菜单
    }

    public enum TypeEnum {
        none,
        view, //跳转URL
        scancode_push, //扫码推事件
        scancode_waitmsg, //扫码推事件且弹出“消息接收中”提示框
        pic_photo_or_album, //弹出拍照或者相册发图
        pic_weixin, //弹出微信相册发图器
        location_select, //弹出地理位置选择器
        media_id, //下发消息（除文本消息）
        view_limited, //跳转图文消息URL
    }

}

