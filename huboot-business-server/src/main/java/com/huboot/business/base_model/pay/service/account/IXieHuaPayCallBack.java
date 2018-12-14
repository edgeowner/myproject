package com.huboot.business.base_model.pay.service.account;


import com.huboot.business.base_model.pay.dto.account.TradePaySuccessDTO;
import com.huboot.business.base_model.pay.service.account.impl.XieHuaPayCallBackFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Administrator on 2018/1/29 0029.
 */
@FeignClient(name = "huboot", fallback = XieHuaPayCallBackFallBack.class)
public interface IXieHuaPayCallBack {

    @PostMapping(value = "/base_model/order_center/trade/tradeSubPayment/paySuccess")
    void xiehuaPaySuccess(@RequestBody TradePaySuccessDTO dto);
}
