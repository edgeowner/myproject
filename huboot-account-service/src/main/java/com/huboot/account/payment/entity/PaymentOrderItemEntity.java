package com.huboot.account.payment.entity;

import java.io.Serializable;

import com.huboot.commons.jpa.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
*支付订单条目表
*/
@Entity
@Table(name = "ac_payment_order_item")
@DynamicInsert
@DynamicUpdate
@Data
public class PaymentOrderItemEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //支付or退款订单id
    private Long orderId ;
    //商品交易订单号
    private String tradeOrderSn ;
    //订单金额
    private BigDecimal tradeOrderAmount ;
    //交易订单描述
    private String tradeOrderDesc ;
    //交易订单类型
    private String tradeOrderType ;
    //商品名字
    private String goodsName;
    //商品描述
    private String goodsDesc;
}

