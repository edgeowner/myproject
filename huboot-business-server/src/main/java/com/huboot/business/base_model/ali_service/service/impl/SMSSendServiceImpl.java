package com.huboot.business.base_model.ali_service.service.impl;

import com.dahantc.api.sms.json.JSONHttpClient;

import com.huboot.business.base_model.ali_service.config.SMSConfigBean;
import com.huboot.business.base_model.ali_service.dto.SMSDTO;
import com.huboot.business.base_model.ali_service.entity.SystemSMSLogEntity;
import com.huboot.business.base_model.ali_service.repository.ISystemSMSLogRepository;
import com.huboot.business.base_model.ali_service.service.ISMSSendService;
import com.huboot.business.common.component.exception.BizException;
import com.huboot.business.common.enums.SMSNodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Administrator on 2018/1/17 0017.
 */
@Service("SMSSendServiceImpl")
@EnableConfigurationProperties({SMSConfigBean.class})
@Slf4j
public class SMSSendServiceImpl implements ISMSSendService, InitializingBean {

    //
    private JSONHttpClient client;
    @Autowired
    private SMSConfigBean smsConfigBean;
    @Autowired
    private ThreadPoolTaskExecutor smsTaskExecutor;
    @Autowired
    private ISystemSMSLogRepository systemSMSLogRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化发送实例
        client = new JSONHttpClient(smsConfigBean.getUrl());
        client.setRetryCount(smsConfigBean.getRetryCount());
    }

    @Override
    public void send(Integer system, String phones, SMSNodeEnum nodeEnum, Consumer<List<String>> consumer) {
        if(StringUtils.isEmpty(phones)) {
            log.error("发送短信手机号不能为空");
            return;
        }
        if(nodeEnum == null) {
            log.error("发送短信节点不能为空");
            return;
        }
        List<String> list = new ArrayList<>();
        consumer.accept(list);
        SendSMSTask task = new SendSMSTask(phones, nodeEnum, list, system, smsConfigBean);
        smsTaskExecutor.execute(task);
    }

    @Override
    public void send(SMSDTO dto) {
        if(StringUtils.isEmpty(dto.getPhones())) {
            throw new BizException("发送短信手机号不能为空");
        }
        if(dto.getNode() == null) {
            throw new BizException("发送短信节点不能为空");
        }
        SendSMSTask task = new SendSMSTask(dto.getPhones(), dto.getNode(), dto.getParams(), dto.getSystem().ordinal(), smsConfigBean);
        smsTaskExecutor.execute(task);
    }

    private class SendSMSTask implements Runnable {

        private String phones;
        private SMSNodeEnum nodeEnum;
        private List<String> list;
        private Integer system;
        private SMSConfigBean smsConfigBean;

        public SendSMSTask(String phones, SMSNodeEnum nodeEnum, List<String> list, Integer system, SMSConfigBean smsConfigBean) {
            this.phones = phones;
            this.nodeEnum = nodeEnum;
            this.list = list;
            this.smsConfigBean = smsConfigBean;
            this.system = system;
        }

        @Override
        public void run() {
            String request = nodeEnum.getContent();
            if(!CollectionUtils.isEmpty(list)) {
                String[] strpramas = list.toArray(new String[list.size()]);
                request = MessageFormat.format(request, strpramas);
            }
            String response = "";
            try {
                if(smsConfigBean.isOpen()) {
                    response = client.sendSms(smsConfigBean.getAccount(),
                            smsConfigBean.getPassword(), phones, request, smsConfigBean.getSign(), smsConfigBean.getSubCode());
                } else {
                    response = "系统已关闭短信服务";
                }
            } catch (Exception e) {
                response = e.getMessage();
            } finally {
                SystemSMSLogEntity logEntity = new SystemSMSLogEntity();
                logEntity.setSystem(system);
                logEntity.setNode(nodeEnum.getNode());
                logEntity.setPhone(phones);
                logEntity.setSmsRequest(request);
                logEntity.setSmsResponse(response);
                systemSMSLogRepository.create(logEntity);
            }
        }
    }
}
