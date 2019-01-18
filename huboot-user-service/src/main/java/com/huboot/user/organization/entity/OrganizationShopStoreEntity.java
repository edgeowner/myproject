package com.huboot.user.organization.entity;

import com.huboot.commons.jpa.AbstractEntity;
import com.huboot.share.common.enums.YesOrNoEnum;
import com.huboot.share.user_service.enums.OrganizationShopStoreTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * 组织-店铺门店表
 * 防止无限递归：StackOverflowError
 * https://blog.csdn.net/li_xiang_lin/article/details/78738147
 * https://blog.csdn.net/code_du/article/details/37809567
 * https://blog.csdn.net/a499477783/article/details/79969750
 */
@Entity
@Table(name = "us_organization_shop_store")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class OrganizationShopStoreEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //店铺id
    private Long shopId;
    //类型-枚举
    @Enumerated(EnumType.STRING)
    private OrganizationShopStoreTypeEnum type;
    //名称
    private String name;
    //地区
    private Long areaId;
    //地址
    private String address;
    //默认-枚举
    @Enumerated(EnumType.STRING)
    private YesOrNoEnum defaultStatus;

}

