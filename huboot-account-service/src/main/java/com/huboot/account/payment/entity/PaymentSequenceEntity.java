package com.huboot.account.payment.entity;

import java.io.Serializable;

import com.huboot.commons.jpa.AbstractEntity;
import com.huboot.share.account_service.enums.PayPlatformEnum;
import com.huboot.share.account_service.enums.PayStatusEnum;
import com.huboot.share.account_service.enums.PayTypeEnum;
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
*支付流水表
*/
@Entity
@Table(name = "ac_payment_sequence")
@DynamicInsert
@DynamicUpdate
@Data
public class PaymentSequenceEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //支付单or退款单id
    private Long orderId ;
    //应付金额
    private BigDecimal payableAmount ;
    //实际支付金额
    private BigDecimal actualPayAmount ;
    //优惠金额
    private BigDecimal discountAmount ;
    //支付状态
    @Enumerated(EnumType.STRING)
    private PayStatusEnum payStatus ;
    //成功支付时间
    private LocalDateTime payTime ;
    //支付平台
    @Enumerated(EnumType.STRING)
    private PayPlatformEnum payPlatform ;
    //支付方式
    @Enumerated(EnumType.STRING)
    private PayTypeEnum payType ;
    //在线支付三方请求
    private String tripartiteRequest ;
    //在线支付三方相应
    private String tripartiteResponse ;
    //在线支付三方流水id
    private String tripartiteId ;
    //三方流水号
    private String tripartiteSeq ;
    //易宝单据token
    private String yibaoToken;
    //微信openid
    private String weixinOpenid;
    //第三方支付返回
    private String yibaoPayResp;

}

