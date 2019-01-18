package com.huboot.user.role.entity;

import com.huboot.commons.jpa.AbstractEntity;
import com.huboot.commons.jpa.JpaConverterJson;
import com.huboot.share.user_service.enums.OrganizationSystemEnum;
import com.huboot.user.permission.entity.PermissionEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * 用户服务-角色表
 * 防止无限递归：StackOverflowError
 * https://blog.csdn.net/li_xiang_lin/article/details/78738147
 * https://blog.csdn.net/code_du/article/details/37809567
 * https://blog.csdn.net/a499477783/article/details/79969750
 */
@Entity
@Table(name = "us_role")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class RoleEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //名称
    private String name;
    //组织ID-公司根节点
    private Long organizationId;
    //名称
    private String description;
    //颜色,产品要求:颜色，前端渲染需要
    private String colour;
    //所属系统-json数组
    @Convert(converter = JpaConverterJson.class)
    private List<OrganizationSystemEnum> system;

    //自定义属性 -资源信息
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roleEntity")
    private List<PermissionEntity> permissionEntities;
}

