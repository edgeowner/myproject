package com.huboot.business.base_model.ali_service.service.impl;

import com.dahantc.api.sms.json.JSONHttpClient;

import com.huboot.business.base_model.ali_service.config.SMSConfigBean;
import com.huboot.business.base_model.ali_service.dto.SMSConstant;
import com.huboot.business.base_model.ali_service.dto.SMSDTO;
import com.huboot.business.base_model.ali_service.dto.SMSDTO2;
import com.huboot.business.base_model.ali_service.dto.SmsVO;
import com.huboot.business.base_model.ali_service.entity.SystemSMSLogEntity;
import com.huboot.business.base_model.ali_service.repository.ISystemSMSLogRepository;
import com.huboot.business.base_model.ali_service.service.ISMSSendService;
import com.huboot.business.common.component.exception.BizException;
import com.huboot.business.common.enums.SMSNodeEnum;
import com.huboot.business.common.utils.DateUtil;
import com.huboot.business.common.utils.Tools;
import com.huboot.business.mybatis.ApiException;
import com.huboot.business.mybatis.Constant;
import com.huboot.business.mybatis.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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
    @Autowired
    private RedisTemplate redisTemplate;

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


    /*发送验证码：生成验证码，先保存在redis，在发送出去，
    用户再次提交时，会带这个验证码，再从redis中拿出进行验证码校验
    */

    @Override
    public void sendValidateCode(String phone) {

        if (StringUtils.isEmpty(phone) || !Tools.checkMobile(phone)) {
            throw new ApiException(ErrorCodeEnum.NOTNULL, "手机号有误");
        }
        /*UserDomain userDomain = userService.findByUsername(phone);
        if (null != userDomain) {
            throw new ApiException(ErrorCodeEnum.UserExists, "手机号已存在");
        }*/
        SmsVO smsVO = new SmsVO();
        //校验发送次数
        checkSendNum(phone, smsVO);
        SMSDTO2 smsDTO = new SMSDTO2();
        List<String> mobiles = new ArrayList<>();
        mobiles.add(phone);
        smsDTO.setMobiles(mobiles);
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();

        //保存验证码
        String code = Tools.getRandom(6);
        //ValueOperations,spring的redis的类，这里set一下，就已经保存到redis去了
        valueOperations.set(String.format(SMSConstant.SMS_REGISTER_CODE, phone), code, Constant.SEC_TENMINUTE, TimeUnit.SECONDS);
        Map<String, String> contentMap = new HashMap<>();
        contentMap.put("code", code);
        contentMap.put("minutes", SMSConstant.SMS_REGISTER_EXPIRY);
        smsDTO.setContentMap(contentMap);

        //最后发送
        SMSDTO dto = new SMSDTO();
        this.send(dto);
        //smsService.sendSms(SmsTemplateTypeEnum.PhoneRegister, smsDTO);
    }


    private void checkSendNum(String phone, SmsVO smsVO) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        //判断单个手机号每天发送的次数
        Integer sms_register_num_result = (Integer) valueOperations.get(String.format(SMSConstant.SMS_REGISTER_NUM, phone));
        Integer sms_register_num = 0;
        if (null != sms_register_num_result) {
            sms_register_num = sms_register_num_result;
        }
        if (sms_register_num >= SMSConstant.SMS_REGISTER_MAX_NUM) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "短信获取次数已达当天上限，请稍后重试，或联系客服！");
        }
        sms_register_num++;
        if (sms_register_num == SMSConstant.SMS_REGISTER_MAX_NUM) {
            smsVO.setContent("发送成功，短信获取次数已达当天上限");
        } else if (sms_register_num > SMSConstant.SMS_REGISTER_PROMPT_NUM) {
            int num = SMSConstant.SMS_REGISTER_MAX_NUM - sms_register_num;
            smsVO.setContent("发送成功，剩余" + num + "次发送机会！");
        }
        valueOperations.set(String.format(SMSConstant.SMS_REGISTER_NUM, phone), sms_register_num);
        redisTemplate.expireAt(String.format(SMSConstant.SMS_REGISTER_NUM, phone), DateUtil.getTimesNight());
        smsVO.setSendNum(sms_register_num);
    }

}
