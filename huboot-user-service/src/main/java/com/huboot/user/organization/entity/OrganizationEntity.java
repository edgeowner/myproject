package com.huboot.user.organization.entity;

import com.huboot.commons.jpa.AbstractEntity;
import com.huboot.commons.jpa.JpaConverterJson;
import com.huboot.share.user_service.enums.OrganizationSystemEnum;
import com.huboot.user.user.entity.UserEmployeeEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * 用户服务-部门基础表
 */
@Entity
@Table(name = "us_organization")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class OrganizationEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //组织名称
    private String name;
    //组织状态
    private String status;
    //组织性质
    private String type;
    //上级组织ID
    private Long parentId;
    //所属系统-json数组
    @Convert(converter = JpaConverterJson.class)
    private List<OrganizationSystemEnum> system;
    //路径-json数组
    @Convert(converter = JpaConverterJson.class)
    private List<Long> path;

    //自定义属性 -公司信息
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "organizationEntity")
    private OrganizationCompanyEntity organizationCompanyEntity;
    //自定义属性 -店铺信息
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organizationEntity")
    private List<OrganizationShopEntity> organizationShopEntity;
    //自定义属性 -员工信息
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organizationEntity")
    private List<UserEmployeeEntity> userEmployeeEntity;

    //自定义属性 -父节点
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId", referencedColumnName = "id", insertable = false, updatable = false)
    private OrganizationEntity parentOrganizationEntity;
    //自定义属性 -子节点
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "parentId", insertable = false, updatable = false)
    private List<OrganizationEntity> childrenOrganizationEntity;
}

