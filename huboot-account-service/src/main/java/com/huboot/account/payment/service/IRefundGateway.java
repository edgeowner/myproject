package com.huboot.account.payment.service;

import com.huboot.account.payment.entity.PaymentOrderEntity;
import com.huboot.account.payment.entity.PaymentSequenceEntity;

import java.util.Map;

/**
 * Created by Administrator on 2018/8/30 0030.
 */
public interface IRefundGateway {


    /**
     *
     * @param refundEntity
     * @param extendMap
     * @return
     */
    PaymentSequenceEntity refund(PaymentOrderEntity refundEntity, Map<String, Object> extendMap);

}
