package com.huboot.user.organization.entity;

import com.huboot.commons.jpa.AbstractEntity;
import com.huboot.commons.jpa.JpaConverterJson;
import com.huboot.share.user_service.enums.OrganizationShopBusinessTypeEnum;
import com.huboot.share.user_service.enums.OrganizationShopStatusEnum;
import com.huboot.share.user_service.enums.OrganizationSystemEnum;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import lombok.Data;

import java.util.List;

/**
*用户服务-部门店铺表
*/
@Entity
@Table(name = "us_organization_shop")
@DynamicInsert
@DynamicUpdate
@Data
public class OrganizationShopEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //根组织ID
    private Long organizationId ;
    //店铺经营模式-枚举
    @Enumerated(EnumType.STRING)
    private OrganizationShopBusinessTypeEnum businessType ;
    //服务窗编号
    private String sn ;
    //名称
    private String name ;
    //logo原图路径
    private String logoPath ;
    //地区
    private Long areaId ;
    //联系地址
    private String address ;
    //联系人
    private String contract ;
    //联系电话
    private String phone ;
    //店铺状态-枚举
    @Enumerated(EnumType.STRING)
    private OrganizationShopStatusEnum status ;
    //所属系统-json数组
    @Convert(converter = JpaConverterJson.class)
    private List<OrganizationSystemEnum> system;
    //
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizationId", referencedColumnName = "id", insertable = false, updatable = false)
    private OrganizationEntity organizationEntity;

}

