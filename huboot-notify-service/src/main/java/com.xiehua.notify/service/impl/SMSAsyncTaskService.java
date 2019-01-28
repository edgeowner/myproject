package com.xiehua.notify.service.impl;

import com.huboot.commons.utils.JsonUtil;
import com.huboot.share.notify_service.enums.SMSSendStatusEnum;
import com.huboot.share.notify_service.enums.SMSTaskStatusEnum;
import com.xiehua.notify.config.SMSConfig;
import com.xiehua.notify.dto.SMSSendResult;
import com.xiehua.notify.entity.SMSLog;
import com.xiehua.notify.entity.SMSSubTask;
import com.xiehua.notify.entity.SMSTask;
import com.xiehua.notify.repository.ISMSLogRepository;
import com.xiehua.notify.repository.ISMSSubTaskRepository;
import com.xiehua.notify.repository.ISMSTaskRepository;
import com.xiehua.notify.service.SMSSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@Slf4j
@Component
public class SMSAsyncTaskService  {

    private static final Integer COUNT_PER = 200;

    @Autowired
    private ISMSTaskRepository taskRepository;
    @Autowired
    private ISMSSubTaskRepository subTaskRepository;
    @Autowired
    private ISMSLogRepository smsLogRepository;
    @Autowired
    @Qualifier("dhstSMSSendService")
    private SMSSendService sendService;


    @Async
    public void sending(String taksId) {
        SMSTask task = taskRepository.findById(taksId).get();
        //差分子任务
        List<String> phoneList = JsonUtil.buildNormalMapper().fromJsonToList(task.getPhones(), String.class);
        List<SMSSubTask> subTaskList = new ArrayList<>();
        int s = phoneList.size() / COUNT_PER;
        int m = phoneList.size() % COUNT_PER;
        for(int i = 0; i < s; i++) {
            List<String> subPhoneList = phoneList.subList((i * COUNT_PER), ((i + 1) * COUNT_PER));
            subTaskList.add(createSubTask(subPhoneList, taksId));
        }
        if(m != 0) {
            List<String> subPhoneList = phoneList.subList((s * COUNT_PER), ((s * COUNT_PER) + m));
            subTaskList.add(createSubTask(subPhoneList, taksId));
        }
        Long totalSuccess = 0l;
        Long totalFailure = 0l;
        for(SMSSubTask subTask : subTaskList) {
            SMSSendResult result = sendService.sendRequest(subTask.getPhones(), task.getSign(),
                    task.getContent(), task.getType());
            subTask.setResponseBody(result.getResponse());
            if(result.isSuccess()) {
                subTask.setStatus(SMSSendStatusEnum.success.name());
            } else {
                subTask.setStatus(SMSSendStatusEnum.failure.name());
            }
            subTask.setSuccessNum(Long.valueOf(result.getSuccessPhoneList().size()));
            subTask.setFailureNum(Long.valueOf(result.getFailurePhoneList().size()));
            subTaskRepository.save(subTask);
            totalSuccess += subTask.getSuccessNum();
            totalFailure += subTask.getFailureNum();
            saveSMSLog(result, taksId, subTask.getId(), task.getCode());
        }
        task.setSuccessNum(totalSuccess);
        task.setFailureNum(totalFailure);
        if(task.getSuccessNum().intValue() == 0) {
            task.setStatus(SMSTaskStatusEnum.failure.name());
        } else {
            task.setStatus(SMSTaskStatusEnum.finish.name());
        }
        taskRepository.save(task);
    }

    private  SMSSubTask createSubTask(List<String> subPhoneList, String taskId) {
        SMSSubTask subTask = new SMSSubTask();
        subTask.setCreateTime(LocalDateTime.now());
        subTask.setPhones(String.join(",", subPhoneList));
        subTask.setTaskId(taskId);
        subTask.setSuccessNum(0l);
        subTask.setFailureNum(0l);
        subTask.setStatus(SMSSendStatusEnum.none.name());
        subTaskRepository.save(subTask);
        return subTask;
    }

    private void saveSMSLog(SMSSendResult result, String taksId, String subTaskId, String code) {
        for(String phone : result.getSuccessPhoneList()) {
            SMSLog log = new SMSLog();
            log.setCreateTime(LocalDateTime.now());
            log.setPhone(phone);
            log.setStatus(SMSSendStatusEnum.success.name());
            log.setTaskId(taksId);
            log.setSubTaskId(subTaskId);
            log.setCode(code);
            smsLogRepository.save(log);
        }
        for(String phone : result.getFailurePhoneList()) {
            SMSLog log = new SMSLog();
            log.setCreateTime(LocalDateTime.now());
            log.setPhone(phone);
            log.setStatus(SMSSendStatusEnum.failure.name());
            log.setTaskId(taksId);
            log.setSubTaskId(subTaskId);
            log.setCode(code);
            log.setErrorReason(result.getErrorReason());
            smsLogRepository.save(log);
        }
    }

}
