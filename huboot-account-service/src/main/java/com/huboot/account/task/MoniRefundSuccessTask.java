package com.huboot.account.task;

import com.huboot.account.payment.service.PayService;
import com.huboot.commons.component.exception.BizException;
import com.huboot.share.account_service.api.dto.yibao.refund.YiBaoRefundResp3DTO;
import com.task.client.DelayTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/1/25 0025.
 */
@Slf4j
@Component
public class MoniRefundSuccessTask implements DelayTask {

    public static final String MONI_REFUND_SUCCESS = "moni_refund_success";

    @Autowired
    private PayService payService;

    @Override
    public String bizName() {
        return MONI_REFUND_SUCCESS;
    }

    @Override
    public void execute(String bizParameters) {
        YiBaoRefundResp3DTO respDTO = new YiBaoRefundResp3DTO();
        respDTO.setRefundRequestId(bizParameters);
        try {
            payService.refundFinish(respDTO);
        } catch (Exception e) {
            log.error("", e);
            throw new BizException("模拟支付成功回掉失败");
        }
    }
}
