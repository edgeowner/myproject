package com.huboot.account.payment.service;

import com.huboot.account.payment.entity.PaymentSequenceEntity;

import java.util.Map;

/**
 * Created by Administrator on 2018/8/31 0031.
 */
public interface IPlatformChannel {

    PaymentSequenceEntity channelPay(PaymentSequenceEntity sequenceEntity, Map<String, Object> extendMap);

    PaymentSequenceEntity channelRefund(PaymentSequenceEntity sequenceEntity, Map<String, Object> extendMap);
}
