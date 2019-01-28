package com.huboot.account.payment.service;


import com.huboot.share.account_service.api.dto.CloseReqDTO;
import com.huboot.share.account_service.api.dto.RefundReqDTO;
import com.huboot.share.account_service.api.dto.RewardPaymentCreateReqDTO;
import com.huboot.share.account_service.api.dto.WriteoffPaymentCreateReqDTO;
import com.huboot.share.account_service.api.dto.order_payment.OPDetailsRespDTO;
import com.huboot.share.account_service.api.dto.order_payment.PayReqDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * 支付订单表Service
 */
public interface IPaymentOrderService {

    //
    String reward(RewardPaymentCreateReqDTO reqDTO);

    //
    String writeoff(WriteoffPaymentCreateReqDTO reqDTO);

    //
    void finish(Long orderId, Long successPayseqId);

    //创建退款
    String refund(RefundReqDTO refundReqDTO);


    //用户交易支付
    String tradePay(PayReqDTO payReqDTO, String ip);

    /***
     * 查询支付单
     *
     * @param paymentSn
     * **/
    OPDetailsRespDTO queryOrderPayment(String paymentSn);

    /***
     * 解冻账户金额
     * **/
    void thawAccountBalance( CloseReqDTO closeReqDTO);

}
