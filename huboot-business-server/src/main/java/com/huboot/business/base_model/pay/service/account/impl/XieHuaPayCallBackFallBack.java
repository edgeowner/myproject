package com.huboot.business.base_model.pay.service.account.impl;


import com.huboot.business.base_model.pay.dto.account.TradePaySuccessDTO;
import com.huboot.business.base_model.pay.service.account.IXieHuaPayCallBack;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Administrator on 2018/1/29 0029.
 */
@Component
public class XieHuaPayCallBackFallBack implements IXieHuaPayCallBack {

    @Override
    public void xiehuaPaySuccess(@RequestBody TradePaySuccessDTO dto) {

    }
}
