package com.huboot.account.payment.service.impl;

import com.huboot.account.account.service.ISubAccountService;
import com.huboot.account.payment.service.IPayGateway;
import com.huboot.account.payment.service.IPlatformChannel;
import com.huboot.account.payment.entity.PaymentOrderEntity;
import com.huboot.account.payment.entity.PaymentSequenceEntity;
import com.huboot.account.payment.repository.IPaymentSequenceRepository;
import com.xiehua.commons.component.exception.BizException;
import com.xiehua.commons.utils.AppAssert;
import com.xiehua.commons.utils.BigDecimalUtil;
import com.xiehua.commons.utils.keyGenerator.PrimaryKeyGenerator;
import com.xiehua.share.account_service.enums.PayPlatformEnum;
import com.xiehua.share.account_service.enums.PayTypeEnum;
import com.xiehua.share.account_service.enums.PaymentOrderStatusEnum;
import com.xiehua.share.account_service.constant.PayConfigConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/30 0030.
 */
@Slf4j
@Service("payGatewayImpl")
public class PayGatewayImpl implements IPayGateway {

    @Autowired
    private ISubAccountService subAccountService;
    @Autowired
    private IPaymentSequenceRepository sequenceRepository;
    @Autowired
    @Qualifier("offlineChannelImpl")
    private IPlatformChannel offlineChannelImpl;
    @Autowired
    @Qualifier("yibaoChannelImpl")
    private IPlatformChannel yibaoChannelImpl;
    @Autowired
    @Qualifier("moniChannelImpl")
    private IPlatformChannel moniChannelImpl;


    /**
     * 支付
     * @param orderEntity
     * @param payType
     * @param extendMap
     * @return
     */
    @Override
    public PaymentSequenceEntity pay(PaymentOrderEntity orderEntity, PayTypeEnum payType, Map<String, Object> extendMap) {
        AppAssert.notNull(orderEntity, "支付单不存在");
        if(!PaymentOrderStatusEnum.submit.equals(orderEntity.getStatus())) {
            throw new BizException("支付单不是待支付状态，不能支付");
        }
        //
        PayPlatformEnum payPlatform = PayConfigConstant.PAYMAP.get(payType);
        AppAssert.notNull(payPlatform, "支付平台没找到，payType=" + payType.name());

        //检查账户是否可用
        subAccountService.checkSubAccountEnableForAll(orderEntity.getFromSubAccount(), orderEntity.getToSubAccount());

        if(extendMap == null) {
            extendMap = new HashMap<>();
        }
        BigDecimal discountAmount = BigDecimal.ZERO;

        Object obj = extendMap.get(PayConfigConstant.DISCOUNT);
        if(obj != null) {
            discountAmount = (BigDecimal)obj;
        }
        if(BigDecimalUtil.lt(discountAmount, BigDecimal.ZERO)) {
            log.warn("优惠金额:" + discountAmount);
            throw new BizException("优惠金额不能小于0");
        }
        if(BigDecimalUtil.gte(discountAmount, orderEntity.getAmount())) {
            log.warn("优惠金额:" + discountAmount + "，应付金额：" + orderEntity.getAmount());
            throw new BizException("优惠金额不能大于或等于应付金额");
        }
        //
        PaymentSequenceEntity sequenceEntity = new PaymentSequenceEntity();
        sequenceEntity.setId(PrimaryKeyGenerator.SEQUENCE.next());
        sequenceEntity.setOrderId(orderEntity.getId());
        sequenceEntity.setPayableAmount(orderEntity.getAmount());
        sequenceEntity.setDiscountAmount(discountAmount);
        sequenceEntity.setActualPayAmount(sequenceEntity.getPayableAmount().subtract(sequenceEntity.getDiscountAmount()));
        sequenceEntity.setPayPlatform(payPlatform);
        sequenceEntity.setPayType(payType);
        //
        if(PayPlatformEnum.offline.equals(payPlatform)) {
            //线下支付
            sequenceEntity = offlineChannelImpl.channelPay(sequenceEntity, extendMap);
        } else if(PayPlatformEnum.moni.equals(payPlatform)) {
            //模拟支付
            sequenceEntity = moniChannelImpl.channelPay(sequenceEntity, extendMap);
        } else {
            sequenceEntity = yibaoChannelImpl.channelPay(sequenceEntity, extendMap);
        }
        sequenceRepository.create(sequenceEntity);
        return sequenceEntity;
    }

}
