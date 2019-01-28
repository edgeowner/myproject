package com.huboot.account.payment.entity;

import java.io.Serializable;

import com.huboot.commons.jpa.AbstractEntity;
import com.huboot.share.account_service.enums.PaymentDiscountTypeEnum;
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
*支付优惠信息控制表
*/
@Entity
@Table(name = "ac_payment_discount")
@DynamicInsert
@DynamicUpdate
@Data
public class PaymentDiscountEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //支付订单id
    private Long paymentOrderId ;
    //优惠类型
    @Enumerated(EnumType.STRING)
    private PaymentDiscountTypeEnum discountType ;
    //子账户id
    private Long subAccountId ;
    //本次支付最多可使用金额
    private BigDecimal maxValue ;

}

