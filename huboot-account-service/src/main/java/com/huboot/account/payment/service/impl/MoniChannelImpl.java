package com.huboot.account.payment.service.impl;

import com.huboot.account.payment.service.IPlatformChannel;
import com.task.client.register.DelayTaskData;
import com.task.client.register.DelayTaskRegister;
import com.huboot.account.payment.entity.PaymentSequenceEntity;
import com.xiehua.account.task.MoniPaySuccessTask;
import com.xiehua.account.task.MoniRefundSuccessTask;
import com.xiehua.commons.component.exception.BizException;
import com.xiehua.commons.utils.DateUtil;
import com.xiehua.commons.utils.JsonUtil;
import com.xiehua.share.account_service.api.dto.yibao.refund.YiBaoRefundRespDTO;
import com.xiehua.share.account_service.constant.PayConfigConstant;
import com.xiehua.share.account_service.enums.PayStatusEnum;
import com.xiehua.share.user_service.data.UserCacheData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/31 0031.
 */
@Service("moniChannelImpl")
public class MoniChannelImpl implements IPlatformChannel {

    @Autowired
    private UserCacheData userCacheData;
    @Autowired
    private DelayTaskRegister delayTaskRegister;

    @Override
    public PaymentSequenceEntity channelPay(PaymentSequenceEntity sequenceEntity, Map<String, Object> extendMap) {
        sequenceEntity.setPayStatus(PayStatusEnum.wait);
        sequenceEntity.setWeixinOpenid(userCacheData.getCurrentUserZkUserThirdOpenId().getValue());
        sequenceEntity.setTripartiteId("");
        sequenceEntity.setYibaoToken("");
        sequenceEntity.setTripartiteRequest("");
        sequenceEntity.setTripartiteResponse("");
        //注册支付成功回调延迟任务(延迟2秒)
        delayTaskRegister.register(DelayTaskData.bizName(MoniPaySuccessTask.MONI_PAY_SUCCESS)
                .bizParameters(sequenceEntity.getId().toString()).executeTime(DateUtil.getDateAddSecond(new Date(), 2)));
        return sequenceEntity;
    }

    @Override
    public PaymentSequenceEntity channelRefund(PaymentSequenceEntity refundSequence, Map<String, Object> extendMap) {
        PaymentSequenceEntity successPaySequence = (PaymentSequenceEntity)extendMap.get("successSequence");
        refundSequence.setPayStatus(PayStatusEnum.wait);
        refundSequence.setTripartiteRequest("");
        refundSequence.setTripartiteResponse("");
        refundSequence.setTripartiteId("");
        refundSequence.setWeixinOpenid(successPaySequence.getWeixinOpenid());
        //注册退款成功回调延迟任务(延迟2秒)
        delayTaskRegister.register(DelayTaskData.bizName(MoniRefundSuccessTask.MONI_REFUND_SUCCESS)
                .bizParameters(refundSequence.getId().toString()).executeTime(DateUtil.getDateAddSecond(new Date(), 5)));
        return refundSequence;
    }
}
