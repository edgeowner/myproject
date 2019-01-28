package com.huboot.account.account.entity;

import java.io.Serializable;

import com.huboot.commons.jpa.AbstractEntity;
import com.huboot.share.account_service.enums.AccountTypeEnum;
import com.huboot.share.common.enums.AbleEnum;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Data;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
*主账户
*/
@Entity
@Table(name = "ac_account")
@DynamicInsert
@DynamicUpdate
@Data
public class AccountEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //账户所属（个人or公司）
    @Enumerated(EnumType.STRING)
    private AccountTypeEnum type ;
    //关联id
    private String relaId ;
    //
    private String relaOrgId;
    //状态
    @Enumerated(EnumType.STRING)
    private AbleEnum status ;

    //商户在易宝平台的分润账户
    private String yibaoMerchantNo;



}

