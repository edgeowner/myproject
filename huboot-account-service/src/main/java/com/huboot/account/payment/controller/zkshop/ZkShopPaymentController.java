package com.huboot.account.payment.controller.zkshop;

import com.huboot.account.payment.service.PayService;
import com.huboot.share.account_service.api.dto.yibao.bakabce.YiBaoBalanceRespDTO;
import com.huboot.share.account_service.api.dto.yibao.tradedivide.TradedivideRespDTO;
import com.huboot.share.account_service.api.feign.ZkShopYiBaoFeignClient;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;

@Api(tags = "直客商家端-支付 API")
@RestController
public class ZkShopPaymentController implements ZkShopYiBaoFeignClient {

    @Autowired
    private PayService payService;


    /**
     * 分账
     **/
    @Override
    public TradedivideRespDTO tradedivide(@RequestParam String userId, @RequestParam String paymentSn, @RequestParam BigDecimal amount) throws IOException {
        return payService.tradedivide(userId, paymentSn, amount);
    }

    /**
     * 分账查询
     **/
    @Override
    public TradedivideRespDTO tradedivideQuery(@RequestParam String divideRequestId) throws IOException {
        return payService.tradedivideQuery(divideRequestId);
    }

    /**
     * 提现
     *
     * @param userId
     **/
    @Override
    public String cashWithdrawal(String userId) {
        return null;
    }

    /**
     * 提现查询
     **/
    @Override
    public String cashWithdrawalQuery() {
        return null;
    }

    /**
     * 余额查询
     * *
     *
     * @param accountId
     */
    @Override
    public YiBaoBalanceRespDTO queryBalance(@RequestParam String accountId) throws IOException {
        return payService.queryBalance(accountId);
    }


}
