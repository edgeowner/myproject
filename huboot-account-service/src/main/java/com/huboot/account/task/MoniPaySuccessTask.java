package com.huboot.account.task;

import com.huboot.account.payment.service.PayService;
import com.huboot.commons.component.exception.BizException;
import com.huboot.share.account_service.api.dto.yibao.pay.YiBaoPayRespDTO;
import com.task.client.DelayTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/1/25 0025.
 */
@Slf4j
@Component
public class MoniPaySuccessTask implements DelayTask {

    public static final String MONI_PAY_SUCCESS = "moni_pay_success";

    @Autowired
    private PayService payService;

    @Override
    public String bizName() {
        return MONI_PAY_SUCCESS;
    }

    @Override
    public void execute(String bizParameters) {
        YiBaoPayRespDTO respDTO = new YiBaoPayRespDTO();
        respDTO.setOrderId(bizParameters);
        try {
            payService.payFinish(respDTO);
        } catch (Exception e) {
            log.error("", e);
            throw new BizException("模拟支付成功回掉失败");
        }
    }
}
