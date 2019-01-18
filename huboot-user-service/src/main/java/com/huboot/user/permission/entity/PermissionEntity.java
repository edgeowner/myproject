package com.huboot.user.permission.entity;

import com.huboot.commons.jpa.AbstractEntity;
import com.huboot.commons.jpa.JpaConverterJson;
import com.huboot.share.user_service.enums.PermissionHttpMethodEnum;
import com.huboot.share.user_service.enums.PermissionTypeEnum;
import com.huboot.user.role.entity.RoleEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * 用户服务-权限表
 * 防止无限递归：StackOverflowError
 * https://blog.csdn.net/li_xiang_lin/article/details/78738147
 * https://blog.csdn.net/code_du/article/details/37809567
 * https://blog.csdn.net/a499477783/article/details/79969750
 */
@Entity
@Table(name = "us_permission")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class PermissionEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //角色ID
    private Long roleId;
    //资源url，支持Ant
    @Convert(converter = JpaConverterJson.class)
    private List<String> resourcesUrl;
    //资源请求方法-枚举
    @Convert(converter = JpaConverterJson.class)
    private List<PermissionHttpMethodEnum> resourcesMethod;
    //类型-枚举
    @Enumerated(EnumType.STRING)
    private PermissionTypeEnum type;
    //描述
    private String description;

    //角色
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId", referencedColumnName = "id", insertable = false, updatable = false)
    private RoleEntity roleEntity;
}

