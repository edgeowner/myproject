package com.huboot.account.payment.entity;

import java.io.Serializable;

import com.huboot.commons.jpa.AbstractEntity;
import com.huboot.share.account_service.enums.PaymentOrderSourceEnum;
import com.huboot.share.account_service.enums.PaymentOrderStatusEnum;
import com.huboot.share.account_service.enums.PaymentOrderTypeEnum;
import com.huboot.share.common.enums.YesOrNoEnum;
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
*支付订单表
*/
@Entity
@Table(name = "ac_payment_order")
@DynamicInsert
@DynamicUpdate
@Data
public class PaymentOrderEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //支付单类型
    @Enumerated(EnumType.STRING)
    private PaymentOrderTypeEnum type ;
    //编号
    private String sn ;
    //付钱方子账户
    private Long fromSubAccount ;
    //收钱方子账户
    private Long toSubAccount ;
    //来源
    @Enumerated(EnumType.STRING)
    private PaymentOrderSourceEnum source ;
    //交易金额
    private BigDecimal amount ;
    //状态
    @Enumerated(EnumType.STRING)
    private PaymentOrderStatusEnum status ;
    //过期时间
    private LocalDateTime expireTime ;
    //成功付款后是否需要冻结资金 
    @Enumerated(EnumType.STRING)
    private YesOrNoEnum freezeAfterPaid ;
    //退款关联支付单号
    private String refundRelaPaySn ;
    //成功支付流水id
    private Long successPayseqId ;


}

