package com.huboot.account.payment.entity;

import com.huboot.commons.jpa.AbstractEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
*易宝分账表
*/
@Entity
@Table(name = "ac_payment_yibao_divide")
@DynamicInsert
@DynamicUpdate
@Data
public class PaymentYibaoDivideEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    public static final String STATUS_ACCEPT = "ACCEPT";

    public static final String STATUS_SUCCESS = "SUCCESS";

    public static final String STATUS_FAIL = "FAIL";

    private String paymentOrderSn;//账单编号

    private String merchantNo;//商户编号

    private String merchantName;//商户简称

    private BigDecimal amount;//交易金额

    private String status;//状态

    private String reqId;//请求编号

    private String req;//请求

    private String resp;//响应




}

