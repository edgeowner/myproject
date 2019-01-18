package com.huboot.user.weixin.entity;

import com.huboot.commons.jpa.AbstractEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
*小程序微信用户
*/
@Entity
@Table(name = "us_miniapp_user")
@DynamicInsert
@DynamicUpdate
@Data
public class MiniappUserEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //nimiapp_id
    private String miniappId ;
    //open_id
    private String openId ;
    //
    private String sessionKey;
    //
    private String unionId;

}

