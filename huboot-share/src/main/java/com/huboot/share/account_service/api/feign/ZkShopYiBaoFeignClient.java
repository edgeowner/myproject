package com.huboot.share.account_service.api.feign;

import com.huboot.share.account_service.api.dto.yibao.bakabce.YiBaoBalanceRespDTO;
import com.huboot.share.account_service.api.dto.yibao.tradedivide.TradedivideRespDTO;
import com.huboot.share.common.constant.ServiceName;
import com.huboot.share.common.feign.FeignCilentConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.math.BigDecimal;

@FeignClient(name = ServiceName.ACCOUNT_SERVICE, configuration = FeignCilentConfig.class)
public interface ZkShopYiBaoFeignClient {

    /**
     * 分账
     * **/
    @PostMapping(value = "/zkshop/account/payment/tradedivide")
    TradedivideRespDTO tradedivide(@RequestParam String userId, @RequestParam String paymentSn, @RequestParam BigDecimal amount) throws IOException;

    /**
     * 分账查询
     * **/
    @PostMapping(value = "/zkshop/account/payment/tradedivide_query")
    TradedivideRespDTO tradedivideQuery(@RequestParam String divideRequestId) throws IOException;

    /**
     * 提现
     * **/
    @PostMapping(value = "/zkshop/account/payment/cash_withdrawal")
    String cashWithdrawal(@RequestParam String userId);

    /**
     * 提现查询
     * **/
    @PostMapping(value = "/zkshop/account/payment/cash_withdrawal_query")
    String cashWithdrawalQuery();


    /**
     * 余额查询
     ***/
    @GetMapping(value = "/zkshop/account/payment/cash_query")
    YiBaoBalanceRespDTO queryBalance(@RequestParam String accountId) throws IOException;


}
