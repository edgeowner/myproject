package com.huboot.account.payment.service.impl;

import com.huboot.account.account.service.ISubAccountService;
import com.huboot.account.payment.entity.PaymentOrderEntity;
import com.huboot.account.payment.entity.PaymentSequenceEntity;
import com.huboot.account.payment.repository.IPaymentSequenceRepository;
import com.huboot.account.payment.service.IPlatformChannel;
import com.huboot.account.payment.service.IRefundGateway;
import com.xiehua.commons.utils.AppAssert;
import com.xiehua.commons.utils.keyGenerator.PrimaryKeyGenerator;
import com.xiehua.share.account_service.enums.PayPlatformEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by Administrator on 2019/1/25 0025.
 */
@Component
public class RefundGatewayImpl implements IRefundGateway {

    @Autowired
    private ISubAccountService subAccountService;
    @Autowired
    @Qualifier("offlineChannelImpl")
    private IPlatformChannel offlineChannelImpl;
    @Autowired
    @Qualifier("yibaoChannelImpl")
    private IPlatformChannel yibaoChannelImpl;
    @Autowired
    @Qualifier("moniChannelImpl")
    private IPlatformChannel moniChannelImpl;
    @Autowired
    private IPaymentSequenceRepository sequenceRepository;

    /**
     *
     * @param refundEntity
     * @param extendMap
     * @return
     */
    @Override
    public PaymentSequenceEntity refund(PaymentOrderEntity refundEntity, Map<String, Object> extendMap) {
        AppAssert.notNull(refundEntity, "退款单不存在");
        //检查账户是否可用
        subAccountService.checkSubAccountEnableForAll(refundEntity.getFromSubAccount(), refundEntity.getToSubAccount());

        PaymentSequenceEntity successSequence = (PaymentSequenceEntity)extendMap.get("successSequence");

        PaymentSequenceEntity sequenceEntity = new PaymentSequenceEntity();
        sequenceEntity.setId(PrimaryKeyGenerator.SEQUENCE.next());
        sequenceEntity.setOrderId(refundEntity.getId());
        sequenceEntity.setPayableAmount(refundEntity.getAmount());
        sequenceEntity.setDiscountAmount(BigDecimal.ZERO);
        sequenceEntity.setActualPayAmount(refundEntity.getAmount());
        sequenceEntity.setPayPlatform(successSequence.getPayPlatform());
        sequenceEntity.setPayType(successSequence.getPayType());
        //
        if(PayPlatformEnum.offline.equals(sequenceEntity.getPayPlatform())) {
            //线下退款
            sequenceEntity = offlineChannelImpl.channelRefund(sequenceEntity, extendMap);
        } else if(PayPlatformEnum.moni.equals(sequenceEntity.getPayPlatform())) {
            //模拟退款
            sequenceEntity = moniChannelImpl.channelRefund(sequenceEntity, extendMap);
        } else {
            sequenceEntity = yibaoChannelImpl.channelRefund(sequenceEntity, extendMap);
        }
        sequenceRepository.create(sequenceEntity);
        return sequenceEntity;
    }
}
