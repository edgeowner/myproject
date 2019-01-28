package com.huboot.account.payment.controller.zkuser;

import com.huboot.account.payment.service.IPaymentOrderService;
import com.huboot.account.payment.service.PayService;
import com.huboot.commons.utils.NetworkUtil;
import com.huboot.share.account_service.api.dto.order_payment.PayReqDTO;
import com.huboot.share.account_service.enums.PayTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Api(tags = "直客用户端-支付 API")
@RestController
public class ZkUserPaymentController {

    @Autowired
    private PayService payService;
    @Autowired
    private IPaymentOrderService paymentOrderService;

    /***
     * 支付唤起
     * @return String 易宝支付token
     * **/
    @ApiOperation("支付唤起")
    @PostMapping(value = "/zkuser/account/payment/pay")
    public String pay(@Validated @RequestBody PayReqDTO payReqDTO, HttpServletRequest request) throws Exception{
        String ip = NetworkUtil.getIpAddress(request);
        //return payService.pay(payReqDTO, StringUtils.isEmpty(ip) ? "127.0.0.1" : ip);
        ip = StringUtils.isEmpty(ip) ? "127.0.0.1" : ip;
        return paymentOrderService.tradePay(payReqDTO, ip);
    }

    /***
     * 单笔订单查询接口
     * 该接口提供所有易宝支付订单的查询，商户可以通过该接口主动查询订单状态，完成下一步的业务逻辑
     * @return String
     * **/
    @ApiOperation("单笔订单查询接口")
    @GetMapping(value = "/zkuser/account/payment/pay_query")
    public String payQuery(@RequestParam String paymentOrderSn) throws IOException {
        return payService.payQuery(paymentOrderSn);
    }

    @ApiOperation("模拟支付")
    @PostMapping(value = "/zkuser/account/payment/moni_pay")
    public void moniPay(@Validated @RequestBody PayReqDTO payReqDTO, HttpServletRequest request) throws Exception{
        String ip = NetworkUtil.getIpAddress(request);
        ip = StringUtils.isEmpty(ip) ? "127.0.0.1" : ip;
        payReqDTO.setPayType(PayTypeEnum.xiehua_moni);
        paymentOrderService.tradePay(payReqDTO, ip);
    }

}
