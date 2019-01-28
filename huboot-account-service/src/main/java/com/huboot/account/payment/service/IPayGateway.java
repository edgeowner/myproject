package com.huboot.account.payment.service;

import com.huboot.account.payment.entity.PaymentOrderEntity;
import com.huboot.account.payment.entity.PaymentSequenceEntity;
import com.huboot.share.account_service.enums.PayTypeEnum;

import java.util.Map;

/**
 * Created by Administrator on 2018/8/30 0030.
 */
public interface IPayGateway {


    /**
     * 支付
     * @param orderEntity
     * @param payType
     * @param extendMap
     * @return
     */
    PaymentSequenceEntity pay(PaymentOrderEntity orderEntity, PayTypeEnum payType, Map<String, Object> extendMap);

}
