package com.huboot.account.account.entity;

import com.huboot.commons.jpa.AbstractEntity;
import com.huboot.share.account_service.enums.SubAccountTypeEnum;
import com.huboot.share.common.enums.AbleEnum;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import lombok.Data;

import java.math.BigDecimal;

/**
*子账户
*/
@Entity
@Table(name = "ac_sub_account")
@DynamicInsert
@DynamicUpdate
@Data
public class SubAccountEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //主账户id
    private Long accountId ;
    //子账户类型
    @Enumerated(EnumType.STRING)
    private SubAccountTypeEnum type ;
    //子账户状态
    @Enumerated(EnumType.STRING)
    private AbleEnum status ;
    //总余额
    private BigDecimal totalBalance ;
    //可用余额
    private BigDecimal usableBalance ;
    //不可用余额(冻结)
    private BigDecimal unusableBalance ;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", referencedColumnName = "id", insertable = false, updatable = false)
    private AccountEntity account;

}

