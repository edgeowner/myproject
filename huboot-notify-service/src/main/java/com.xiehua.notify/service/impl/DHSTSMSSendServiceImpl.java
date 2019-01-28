package com.xiehua.notify.service.impl;

import com.dahantc.api.sms.json.JSONHttpClient;
import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.utils.JsonUtil;
import com.huboot.share.notify_service.enums.SMSTaskTypeEnum;
import com.xiehua.notify.config.SMSConfig;
import com.xiehua.notify.dto.SMSSendResult;
import com.xiehua.notify.service.SMSSendService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by Administrator on 2018/8/28 0028.
 */
@Slf4j
@Service("dhstSMSSendService")
@EnableConfigurationProperties({SMSConfig.class})
public class DHSTSMSSendServiceImpl implements SMSSendService, InitializingBean {

    //
    private JSONHttpClient client;
    @Autowired
    private SMSConfig smsConfig;

    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化发送实例
        client = new JSONHttpClient(smsConfig.getUrl());
        client.setRetryCount(smsConfig.getRetryCount());
    }

    @Override
    public SMSSendResult sendRequest(String phones, String sign, String content, String type) {
        String response;
        try {
            String account;
            String password;
            if(SMSTaskTypeEnum.notice.name().equals(type)) {
                account = smsConfig.getAccount1();
                password = smsConfig.getPassword1();
            } else if(SMSTaskTypeEnum.marketing.name().equals(type)) {
                account = smsConfig.getAccount2();
                password = smsConfig.getPassword2();
            } else {
                throw new BizException("短信类型错误");
            }
            if(smsConfig.isOpen()) {
                response = client.sendSms(account, password, phones, content, sign, smsConfig.getSubCode());
            } else {
                response = "{\"msgid\": \"\",\"result\": \"0\",\"desc\": \"系统已关闭短信服务\",\"failPhones\": \"\"}";
            }
        } catch (Exception e) {
            response = "{\"msgid\": \"\",\"result\": \"200\",\"desc\": \"" + e.getMessage() + "\",\"failPhones\": \"" + phones + "\"}";
        }
        return analysisResponse(response, phones);
    }

    @Data
    static class SendResult {
        private String msgid;
        private Integer result;
        private String desc;
        private String failPhones;
    }

    /**
     * 解析结果
     * @param response
     */
    private SMSSendResult analysisResponse(String response, String phones) {
        SMSSendResult sendResult = new SMSSendResult();
        sendResult.setResponse(response);
        SendResult result = JsonUtil.buildNormalMapper().fromJson(response, SendResult.class);
        if(result.getResult() == 0) {
            sendResult.setSuccess(true);
        } else {
            sendResult.setErrorReason(result.getDesc());
            sendResult.setSuccess(false);
        }
        sendResult.setFailurePhoneList(getPhoneList(result.getFailPhones()));
        if(CollectionUtils.isEmpty(sendResult.getFailurePhoneList())) {
            sendResult.setSuccessPhoneList(getPhoneList(phones));
        } else {
            List<String> list = getPhoneList(phones);
            list.removeAll(sendResult.getFailurePhoneList());
            sendResult.setSuccessPhoneList(list);
        }
        return sendResult;
    }

    private List<String> getPhoneList(String phones) {
        List<String> list = new ArrayList<>();
        if(StringUtils.isEmpty(phones)) {
            return list;
        }
        String[] strs = phones.split(",");
        for(String str : strs) {
            list.add(str);
        }
        return list;
    }

}
