package com.huboot.user.user.entity;

import com.huboot.commons.jpa.AbstractEntity;
import com.huboot.share.user_service.enums.UserEmployeeStatusEnum;
import com.huboot.user.organization.entity.OrganizationEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 用户服务-企业信息表
 * 防止无限递归：StackOverflowError
 * https://blog.csdn.net/li_xiang_lin/article/details/78738147
 * https://blog.csdn.net/code_du/article/details/37809567
 * https://blog.csdn.net/a499477783/article/details/79969750
 */
@Entity
@Table(name = "us_user_employee")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class UserEmployeeEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //用户ID
    private Long userId;
    //工号
    private String jobNumber;
    //状态-枚举
    @Enumerated(EnumType.STRING)
    private UserEmployeeStatusEnum status;
    //员工所属组织ID
    private Long organizationId ;
    //自定义添加
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizationId", referencedColumnName = "id", insertable = false, updatable = false)
    private OrganizationEntity organizationEntity;
    //自定义添加
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity userEntity;
}

