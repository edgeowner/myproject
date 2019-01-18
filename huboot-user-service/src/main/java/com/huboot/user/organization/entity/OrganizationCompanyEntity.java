package com.huboot.user.organization.entity;

import com.huboot.commons.jpa.AbstractEntity;
import com.huboot.share.user_service.enums.OrganizationCompanyStatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 用户服务-公司表
 * 防止无限递归：StackOverflowError
 * https://blog.csdn.net/li_xiang_lin/article/details/78738147
 * https://blog.csdn.net/code_du/article/details/37809567
 * https://blog.csdn.net/a499477783/article/details/79969750
 */
@Entity
@Table(name = "us_organization_company")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class OrganizationCompanyEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //根组织ID
    private Long organizationId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizationId", referencedColumnName = "id", insertable = false, updatable = false)
    private OrganizationEntity organizationEntity;
    //公司编号
    private String sn;
    //公司代码
    private String code;
    //注册号/统一社会信用代码
    private String regNum;
    //公司名称
    private String name;
    //简称
    private String abbreviation;
    //地区
    private Long areaId;
    //地址
    private String address;
    //成立时间
    private LocalDateTime buildTime;
    //法人
    private String person;
    //法人身份证
    private String personIdcard;
    //营业执照路径
    private String businessLicensePath;
    //身份证正面路径
    private String idcardFacePath;
    //身份证反面路径
    private String idcardBackPath;
    //工号最后的数字
    private Integer lastNum;
    //审核状态-枚举
    @Enumerated(EnumType.STRING)
    private OrganizationCompanyStatusEnum auditStatus;

}

