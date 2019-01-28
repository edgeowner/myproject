package com.huboot.account.payment.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huboot.account.payment.service.IPlatformChannel;
import com.huboot.account.payment.entity.PaymentOrderItemEntity;
import com.huboot.account.payment.entity.PaymentSequenceEntity;
import com.huboot.account.payment.repository.IPaymentOrderItemRepository;
import com.huboot.account.support.yibao.YiBaoService;
import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.utils.JsonUtil;
import com.huboot.share.account_service.api.dto.order_payment.CreateOPItemReqDTO;
import com.huboot.share.account_service.api.dto.yibao.pay.CashierRespDTO;
import com.huboot.share.account_service.api.dto.yibao.pay.PaySuccessRespDTO;
import com.huboot.share.account_service.api.dto.yibao.refund.YiBaoRefundRespDTO;
import com.huboot.share.account_service.enums.PayStatusEnum;
import com.huboot.share.user_service.data.UserCacheData;
import io.vavr.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/8/31 0031.
 */
@Slf4j
@Service("yibaoChannelImpl")
public class YibaoChannelImpl implements IPlatformChannel {

    @Autowired
    private UserCacheData userCacheData;
    @Autowired
    private YiBaoService yiBaoService;
    @Autowired
    private IPaymentOrderItemRepository orderItemRepository;
    @Autowired
    private ObjectMapper mapper;

    @Override
    public PaymentSequenceEntity channelPay(PaymentSequenceEntity sequenceEntity, Map<String, Object> extendMap) {

        sequenceEntity.setPayStatus(PayStatusEnum.wait);
        sequenceEntity.setWeixinOpenid(userCacheData.getCurrentUserZkUserThirdOpenId().getValue());

        String amount = sequenceEntity.getActualPayAmount().divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "";

        try {
            List<PaymentOrderItemEntity> itemList = orderItemRepository.findByOrderId(sequenceEntity.getOrderId());
            //易宝下单
            PaySuccessRespDTO paySuccessRespDTO = yiBaoService.doOrder(
                    sequenceEntity.getId().toString(),
                    amount,
                    mapper.writeValueAsString(itemList.stream().map(s ->{
                        CreateOPItemReqDTO o = new CreateOPItemReqDTO();
                        BeanUtils.copyProperties(s,o);
                        return o;
                    }).collect(Collectors.toList()).get(0))
            );

            sequenceEntity.setTripartiteId(paySuccessRespDTO.getUniqueOrderNo());
            sequenceEntity.setYibaoToken(paySuccessRespDTO.getToken());

            //请求易宝支付接口
            Tuple2<CashierRespDTO,Map<String,String>>  resp = yiBaoService.pay(
                    paySuccessRespDTO.getToken(),
                    userCacheData.getCurrentUser().getUser().getUserId().toString(),
                    userCacheData.getCurrentUserZkUserThirdOpenId().getValue(),
                    userCacheData.getCurrentUserZkUserThirdOpenId().getAppId(),
                    extendMap.get("ip").toString()
            );

            sequenceEntity.setTripartiteRequest(JsonUtil.buildNormalMapper().toJson(resp._2));
            sequenceEntity.setTripartiteResponse(JsonUtil.buildNormalMapper().toJson(resp._1));

        } catch (Exception e) {
            log.error("", e);
            throw new BizException("获取支付数据失败");
        }

        return sequenceEntity;
    }

    @Override
    public PaymentSequenceEntity channelRefund(PaymentSequenceEntity refundSequence, Map<String, Object> extendMap) {

        refundSequence.setPayStatus(PayStatusEnum.wait);

        PaymentSequenceEntity successPaySequence = (PaymentSequenceEntity)extendMap.get("successSequence");

        String refundAmount = refundSequence.getActualPayAmount().divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "";

        try {
            Tuple2<YiBaoRefundRespDTO,Map<String,String>> result = yiBaoService.refund(
                    successPaySequence.getId().toString(),
                    refundSequence.getId().toString(),
                    successPaySequence.getTripartiteId(),
                    refundAmount
            );
            refundSequence.setTripartiteRequest(JsonUtil.buildNormalMapper().toJson(result._2));
            refundSequence.setTripartiteResponse(JsonUtil.buildNormalMapper().toJson(result._1));
            refundSequence.setTripartiteId(result._1.getUniqueRefundNo());
            refundSequence.setWeixinOpenid(successPaySequence.getWeixinOpenid());
        } catch (Exception e) {
            log.error("", e);
            throw new BizException("退款异常");
        }
        return refundSequence;
    }
}
