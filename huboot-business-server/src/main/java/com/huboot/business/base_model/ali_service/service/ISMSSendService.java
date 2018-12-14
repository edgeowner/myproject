package com.huboot.business.base_model.ali_service.service;


import com.huboot.business.base_model.ali_service.dto.SMSDTO;
import com.huboot.business.common.enums.SMSNodeEnum;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Administrator on 2018/1/17 0017.
 */
public interface ISMSSendService {

    /**
     * 发送短信，如果多个号码，之间用使用","隔开
     * @param phones
     * @param nodeEnum
     * @param consumer
     */
    void send(Integer system, String phones, SMSNodeEnum nodeEnum, Consumer<List<String>> consumer);

    void send(SMSDTO dto);
}
