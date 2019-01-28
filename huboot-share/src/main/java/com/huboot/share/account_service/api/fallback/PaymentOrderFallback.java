package com.huboot.share.account_service.api.fallback;

import com.huboot.commons.component.exception.BizException;
import com.huboot.share.account_service.api.dto.*;
import com.huboot.share.account_service.api.dto.order_payment.CreateOPReqDTO;
import com.huboot.share.account_service.api.dto.order_payment.OPDetailsRespDTO;
import com.huboot.share.account_service.api.dto.yibao.refund.YiBaoRefundResp2DTO;
import com.huboot.share.account_service.api.feign.PaymentOrderFeignClient;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

/**
 * Created by Administrator on 2018/8/31 0031.
 */
@Component
public class PaymentOrderFallback implements PaymentOrderFeignClient {

    @Override
    public String reward(@Validated @RequestBody RewardPaymentCreateReqDTO reqDTO) {
        throw new BizException("请求失败");
    }

    @Override
    public String writeoff(@Validated @RequestBody WriteoffPaymentCreateReqDTO reqDTO) {
        throw new BizException("请求失败");
    }

    /***
     * 创建支付单
     * @return 支付单号
     * @param createOPReqDTO**/
    @Override
    public CreateOrderPaymentRespDTO createOrderPayment(CreateOPReqDTO createOPReqDTO) {
        throw new BizException("创建支付单失败");
    }

    /***
     * 查询支付单
     *
     * @param paymentSn**/
    @Override
    public OPDetailsRespDTO queryOrderPayment(String paymentSn) {
        throw new BizException("查询支付单失败");
    }

    /***
     * 解冻账户金额
     *
     * @param closeReqDTO**/
    @Override
    public void thawAccountBalance(CloseReqDTO closeReqDTO) {
        throw new BizException("解冻账户金额失败");
    }

    /***
     * 支付关闭
     *
     * @param closeReqDTO**/
    @Override
    public void payClose(CloseReqDTO closeReqDTO) throws IOException {
        throw new BizException("支付关闭失败");
    }

    /**
     * 退款
     *
     * @param refundReqDTO
     **/
    @Override
    public CreateOrderPaymentRespDTO refund(RefundReqDTO refundReqDTO) throws IOException {
        throw new BizException("退款失败");
    }

    /**
     * 退款查询接口
     * 提交退款申请后，通过调用该接口查询退款状态。
     *
     * @param refundSn
     **/
    @Override
    public YiBaoRefundResp2DTO refundQuery(String refundSn) throws IOException {
        throw new BizException("退款查询失败");
    }
}
