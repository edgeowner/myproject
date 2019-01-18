package com.huboot.user.organization.entity;

import com.huboot.commons.jpa.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 组织-微页面表
 * 防止无限递归：StackOverflowError
 * https://blog.csdn.net/li_xiang_lin/article/details/78738147
 * https://blog.csdn.net/code_du/article/details/37809567
 * https://blog.csdn.net/a499477783/article/details/79969750
 */
@Entity
@Table(name = "us_organization_shop_micropage")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class OrganizationShopMicropageEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //店铺编号
    private String shopSn;
    //公司介绍-待发布
    private String introductionRc ;
    //公司介绍-发布
    private String introductionRelease ;
    //司机福利-待发布
    private String welfareRc ;
    //司机福利-发布
    private String welfareRelease ;
    //车型详情-待发布
    private String modelRc ;
    //车型详情-发布
    private String modelRelease ;
    //入行指南-待发布
    private String guideRc ;
    //入行指南-发布
    private String guideRelease ;
    //联系方式-待发布
    private String contactRc ;
    //联系方式-发布
    private String contactRelease ;
    //推广-待发布
    private String promotionRc ;
    //推广-发布
    private String promotionRelease ;
    //经纪人-待发布
    private String agentRc ;
    //经纪人-发布
    private String agentRelease ;

}

