package com.huboot.user.user.entity;

import java.io.Serializable;

import com.huboot.commons.jpa.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 用户服务-用户角色关系表
 * 防止无限递归：StackOverflowError
 * https://blog.csdn.net/li_xiang_lin/article/details/78738147
 * https://blog.csdn.net/code_du/article/details/37809567
 * https://blog.csdn.net/a499477783/article/details/79969750
 */
@Entity
@Table(name = "us_user_role_relation")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class UserRoleRelationEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //用户ID
    private Long userId;
    //角色ID
    private Long roleId;

}

