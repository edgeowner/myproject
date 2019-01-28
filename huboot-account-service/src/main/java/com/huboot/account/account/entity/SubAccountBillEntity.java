package com.huboot.account.account.entity;

import java.io.Serializable;

import com.huboot.commons.jpa.AbstractEntity;
import com.huboot.share.account_service.enums.BillTypeEnum;
import com.huboot.share.account_service.enums.PaymentOrderSourceEnum;
import com.huboot.share.account_service.enums.SubAccountStatusEnum;
import com.huboot.share.account_service.enums.SubAccountTypeEnum;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Data;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
*
*/
@Entity
@Table(name = "ac_sub_account_bill")
@DynamicInsert
@DynamicUpdate
@Data
public class SubAccountBillEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //
    private Long accountId;
    //子账户id
    private Long subAccountId ;
    //子帐单类型
    @Enumerated(EnumType.STRING)
    private SubAccountTypeEnum subAccounType ;
    //账单状态
    @Enumerated(EnumType.STRING)
    private SubAccountStatusEnum status ;
    //账单类型
    @Enumerated(EnumType.STRING)
    private BillTypeEnum type ;

    //符号 SignConstant
    private String sign ;
    //账户原金额
    private BigDecimal preAmount ;
    //变动金额
    private BigDecimal amount ;
    //变动后金额
    private BigDecimal afterAmount ;
    //订单id
    private Long orderId ;
    //订单来源
    @Enumerated(EnumType.STRING)
    private PaymentOrderSourceEnum orderSource ;
    //支付流水id
    private Long paymentSeqId ;
    //备注
    private String remark ;

}

