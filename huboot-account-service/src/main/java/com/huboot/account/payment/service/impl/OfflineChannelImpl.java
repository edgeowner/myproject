package com.huboot.account.payment.service.impl;

import com.huboot.account.payment.entity.PaymentSequenceEntity;
import com.huboot.account.payment.repository.IPaymentSequenceRepository;
import com.huboot.account.payment.service.IPlatformChannel;
import com.xiehua.commons.component.exception.BizException;
import com.xiehua.commons.utils.LocalDateTimeUtils;
import com.xiehua.share.account_service.enums.PayStatusEnum;
import com.xiehua.share.account_service.constant.PayConfigConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/8/31 0031.
 */
@Slf4j
@Service("offlineChannelImpl")
public class OfflineChannelImpl implements IPlatformChannel, InitializingBean {

    @Autowired
    private IPaymentSequenceRepository sequenceRepository;
    @Autowired
    private RedisTemplate redisTemplate;

    private RedisAtomicInteger atomicInteger;

    private static final String SERIAL_KEY = "accountSerialNumber";

    @Override
    public void afterPropertiesSet() throws Exception {
        atomicInteger = new RedisAtomicInteger(SERIAL_KEY, redisTemplate.getConnectionFactory());
    }

    @Override
    public PaymentSequenceEntity channelPay(PaymentSequenceEntity sequenceEntity, Map<String, Object> extendMap) {
        sequenceEntity.setPayStatus(PayStatusEnum.success);
        sequenceEntity.setPayTime(LocalDateTime.now());
        String offlineSeq;
        if(extendMap.get(PayConfigConstant.OFFLINESEQ) == null) {
            offlineSeq = generateSeq();
        } else {
            offlineSeq = extendMap.get(PayConfigConstant.OFFLINESEQ).toString();
        }
        if(!StringUtils.isEmpty(offlineSeq)) {
            if(!CollectionUtils.isEmpty(sequenceRepository.findByTripartiteSeq(offlineSeq))) {
                log.warn("流水号已存在,offlineSeq=" + offlineSeq);
                throw new BizException("流水号已存在");
            }
        }
        sequenceEntity.setTripartiteSeq(offlineSeq);
        return sequenceEntity;
    }

    public String generateSeq() {
        String dateStr = LocalDateTimeUtils.formatNow(LocalDateTimeUtils.NDATE);
        Integer num = atomicInteger.incrementAndGet();
        if(num == 1) {
            LocalDateTime end = LocalDateTimeUtils.getDayStart(LocalDateTime.now().plusDays(1));
            Long senond = LocalDateTimeUtils.betweenTwoTime(LocalDateTime.now(), end, ChronoUnit.SECONDS);
            //过期之后又自动从0开始计数器
            atomicInteger.expire(senond, TimeUnit.SECONDS);
        }
        return "A" + dateStr + String.format("%03d", num);
    }

    @Override
    public PaymentSequenceEntity channelRefund(PaymentSequenceEntity refundSequence, Map<String, Object> extendMap) {
        return null;
    }

}
