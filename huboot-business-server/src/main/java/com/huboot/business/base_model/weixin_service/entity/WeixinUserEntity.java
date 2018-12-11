package com.huboot.business.base_model.weixin_service.entity;

import java.io.Serializable;

import com.huboot.business.common.jpa.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
*微信用户表
*/
@Entity
@Table(name = "xiehua_weixin_user")
@DynamicInsert
@DynamicUpdate
@Data
public class WeixinUserEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    private String openId;
    //用户昵称
    private String nickname ;
    //用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    private Integer sex ;
    //用户个人资料填写的省份
    private String province ;
    //普通用户个人资料填写的城市
    private String city ;
    //国家，如中国为CN
    private String country ;
    //用户头像
    private String headimgurl ;
    //关注:0取消关注,1关注
    private Integer subscribe ;
    //是否历史关注过
    private Integer hisSubscribe ;
    //注渠道
    private String subscribeSource;
    //
    private LocalDateTime subscribeTime;

}

