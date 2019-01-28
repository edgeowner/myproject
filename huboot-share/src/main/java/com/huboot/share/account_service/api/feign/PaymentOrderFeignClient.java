package com.huboot.share.account_service.api.feign;

import com.huboot.share.account_service.api.dto.*;
import com.huboot.share.account_service.api.dto.order_payment.CreateOPReqDTO;
import com.huboot.share.account_service.api.dto.order_payment.OPDetailsRespDTO;
import com.huboot.share.account_service.api.dto.yibao.refund.YiBaoRefundResp2DTO;
import com.huboot.share.account_service.api.fallback.PaymentOrderFallback;
import com.huboot.share.common.constant.ServiceName;
import com.huboot.share.common.feign.FeignCilentConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Administrator on 2018/8/31 0031.
 */
@FeignClient(name = ServiceName.ACCOUNT_SERVICE, configuration = FeignCilentConfig.class, fallback = PaymentOrderFallback.class)
public interface PaymentOrderFeignClient {

    /**
     * 奖励
     *
     * @return
     */
    @PostMapping(value = "/inner/account/payment/order/reward")
    public String reward(@Validated @RequestBody RewardPaymentCreateReqDTO reqDTO);

    /**
     * 核销
     *
     * @param reqDTO
     * @return
     */
    @PostMapping(value = "/inner/account/payment/order/writeoff")
    public String writeoff(@Validated @RequestBody WriteoffPaymentCreateReqDTO reqDTO);


    /***
     * 创建支付单
     * @return 支付单号
     * **/
    @PostMapping(value = "/inner/account/payment/order/create")
    CreateOrderPaymentRespDTO createOrderPayment(@Validated @RequestBody CreateOPReqDTO createOPReqDTO) throws IOException, InvocationTargetException, IllegalAccessException;

    /***
     * 查询支付单
     * **/
    @GetMapping(value = "/inner/account/payment/order/q")
    OPDetailsRespDTO queryOrderPayment(@RequestParam("paymentSn") String paymentSn);

    /***
     * 解冻账户金额
     * **/
    @PostMapping(value = "/inner/account/payment/order/thaw_account_balance")
    void thawAccountBalance(@Validated @RequestBody CloseReqDTO closeReqDTO);

    /***
     * 支付关闭
     * **/
    @PostMapping(value = "/inner/account/payment/pay_close")
    void payClose(@Validated @RequestBody CloseReqDTO closeReqDTO) throws IOException;



    /**
     * 退款
     * **/
    @PostMapping(value = "/inner/account/payment/refund")
    CreateOrderPaymentRespDTO refund(@Validated @RequestBody RefundReqDTO refundReqDTO) throws IOException;

    /**
     * 退款查询接口
     * 提交退款申请后，通过调用该接口查询退款状态。
     * **/
    @GetMapping(value = "/inner/account/payment/refund_query")
    YiBaoRefundResp2DTO refundQuery(@RequestParam("refundSn") String refundSn) throws IOException;

}
