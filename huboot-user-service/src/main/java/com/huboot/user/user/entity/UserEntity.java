package com.huboot.user.user.entity;

import com.huboot.commons.jpa.AbstractEntity;
import com.huboot.commons.jpa.JpaConverterJson;
import com.huboot.share.user_service.enums.OrganizationSystemEnum;
import com.huboot.share.user_service.enums.ThirdPlatformEnum;
import com.huboot.share.user_service.enums.UserStatusEnum;
import com.huboot.share.user_service.enums.UserTypeEnum;
import com.huboot.user.organization.entity.OrganizationEntity;
import com.huboot.user.role.entity.RoleEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 用户服务-用户基础信息表
 */
@Entity
@Table(name = "us_user")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class UserEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //全局用户ID
    private Long guid;
    //用户名
    private String username;
    //密码
    private String password;
    //手机号
    private String phone;
    //电子邮箱
    private String email;
    //性别-用户类型
    @Enumerated(EnumType.STRING)
    private UserTypeEnum userType;
    //用户状态-枚举
    @Enumerated(EnumType.STRING)
    private UserStatusEnum userStatus;
    //注册IP
    private String registerIp;
    //注册来源-枚举
    @Enumerated(EnumType.STRING)
    private OrganizationSystemEnum registerSystem;
    //登录失败次数-枚举
    private String loginFailureCount;
    //最后登录IP
    private String latestLoginIp;
    //最后登录时间
    private String latestLoginTime;
    //图片地址(头像)
    private String imagePath;
    //名称(非实名认证)
    private String name;
    //昵称
    private String nickName;
    //根组织ID
    private Long organizationId;
    //第三方openid-json数组
    @Convert(converter = JpaConverterJson.class)
    private List<ThirdOpenId> thirdOpenId;

    //自定义添加
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "userEntity")
    private UserPersonalEntity userPersonalEntity;

    //自定义添加
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "userEntity")
    private UserEmployeeEntity userEmployeeEntity;

    //自定义添加
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizationId", referencedColumnName = "id", insertable = false, updatable = false)
    private OrganizationEntity organizationEntity;

    //自定义添加
    @ManyToMany(targetEntity = RoleEntity.class, fetch = FetchType.LAZY)
    @JoinTable(name = "us_user_role_relation", joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)}, inverseJoinColumns = {@JoinColumn(name = "roleId", referencedColumnName = "id", insertable = false, updatable = false)})
    private List<RoleEntity> roleEntities;

    /**
     * 第三方openid
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ThirdOpenId implements Serializable {
        @Enumerated(value = EnumType.STRING)
        //平台类型
        private ThirdPlatformEnum thirdName;
        //平台类型
        private String appId;
        //值
        private String value;
        //所属系统
        @Enumerated(value = EnumType.STRING)
        private OrganizationSystemEnum system;
    }
}

