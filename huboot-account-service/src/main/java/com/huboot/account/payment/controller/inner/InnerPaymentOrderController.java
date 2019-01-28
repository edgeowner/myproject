package com.huboot.account.payment.controller.inner;

import com.huboot.account.payment.service.IPaymentOrderCreateService;
import com.huboot.account.payment.service.IPaymentOrderService;
import com.huboot.account.payment.service.PayService;
import com.huboot.share.account_service.api.dto.*;
import com.huboot.share.account_service.api.dto.order_payment.CreateOPReqDTO;
import com.huboot.share.account_service.api.dto.order_payment.OPDetailsRespDTO;
import com.huboot.share.account_service.api.dto.yibao.refund.YiBaoRefundResp2DTO;
import com.huboot.share.account_service.api.feign.PaymentOrderFeignClient;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Api(tags = "内部端-支付订单表 API")
@RestController
public class InnerPaymentOrderController implements PaymentOrderFeignClient {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IPaymentOrderService paymentOrderService;
	@Autowired
	private IPaymentOrderCreateService createService;

    @Autowired
    private PayService payService;


    @Override
	@ApiOperation("创建奖励账单")
	public String reward(@Validated @RequestBody RewardPaymentCreateReqDTO reqDTO) {
		return paymentOrderService.reward(reqDTO);
	}

	@Override
	@ApiOperation("创建核销账单")
	public String writeoff(@Validated @RequestBody WriteoffPaymentCreateReqDTO reqDTO) {
		return paymentOrderService.writeoff(reqDTO);
	}

	/***
	 * 创建支付单
	 * @return 支付单号
	 * @param createOPReqDTO**/
	@ApiOperation("创建支付单")
	@Override
	public CreateOrderPaymentRespDTO createOrderPayment(@Validated @RequestBody CreateOPReqDTO createOPReqDTO) throws IOException, InvocationTargetException, IllegalAccessException {
		return createService.createOrderPayment(createOPReqDTO);
	}

    /***
     * 查询支付单
     *
     * @param paymentSn
     * **/
	@ApiOperation("查询支付单支付状态")
    @Override
    public OPDetailsRespDTO queryOrderPayment(@RequestParam String paymentSn) {
        return paymentOrderService.queryOrderPayment(paymentSn);
    }

    /***
     * 解冻账户金额
     * **/
    @ApiOperation("解冻账户金额")
    @Override
    public void thawAccountBalance(@Validated @RequestBody CloseReqDTO closeReqDTO){
        paymentOrderService.thawAccountBalance(closeReqDTO);
    }

	/***
	 * 支付关闭
	 * **/
    @ApiOperation("支付关闭")
	@Override
	public void payClose(@Validated @RequestBody CloseReqDTO closeReqDTO) throws IOException {
		payService.payClose(closeReqDTO);
	}

	/**
	 * 退款
	 *
	 * @param
	 **/
    @ApiOperation("退款")
	@Override
	public CreateOrderPaymentRespDTO refund(@Validated @RequestBody RefundReqDTO refundReqDTO) throws IOException {
		//return payService.refund(refundReqDTO);
		CreateOrderPaymentRespDTO respDTO = new CreateOrderPaymentRespDTO();
		respDTO.setPaymentOrderSn(paymentOrderService.refund(refundReqDTO));
		return respDTO;
	}

	/**
	 * 退款查询接口
	 * 提交退款申请后，通过调用该接口查询退款状态。
	 **/
    @ApiOperation("退款查询接口")
	@Override
	public YiBaoRefundResp2DTO refundQuery(@RequestParam String refundSn) throws IOException {
		return payService.refundQuery(refundSn);
	}

}
