package com.xiehua.notify.service;


import com.huboot.share.notify_service.api.dto.SMSSendDTO;
import com.huboot.share.notify_service.enums.SMSTaskTypeEnum;

import java.util.Map;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
public interface SMSService {


    String sendSMS(SMSSendDTO sendDTO, SMSTaskTypeEnum typeEnum);

    Map smsLogPager(Integer page, Integer limit, String taskId, String phone, String sendStatus, String code);
}
