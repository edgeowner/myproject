package com.huboot.account.payment.service;


import com.huboot.account.payment.entity.PaymentOrderEntity;
import com.huboot.share.account_service.api.dto.CreateOrderPaymentRespDTO;
import com.huboot.share.account_service.api.dto.RewardPaymentCreateReqDTO;
import com.huboot.share.account_service.api.dto.WriteoffPaymentCreateReqDTO;
import com.huboot.share.account_service.api.dto.order_payment.CreateOPReqDTO;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

/**
 *支付订单表Service
 */
public interface IPaymentOrderCreateService {

    //创建奖励订单
    PaymentOrderEntity createRewardOrder(RewardPaymentCreateReqDTO reqDTO);

    //
    PaymentOrderEntity createWriteoffOrder(WriteoffPaymentCreateReqDTO reqDTO);

    //
    PaymentOrderEntity createRefundOrder(PaymentOrderEntity paymentOrder, BigDecimal actualAmount);
    /***
     * 创建支付单
     * @return 支付单号
     * @param createOPReqDTO**/
    CreateOrderPaymentRespDTO createOrderPayment(CreateOPReqDTO createOPReqDTO) throws IOException, InvocationTargetException, IllegalAccessException;

}
