package com.xiehua.notify.service.impl;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.utils.JsonUtil;
import com.huboot.commons.utils.LocalDateTimeUtils;
import com.huboot.share.notify_service.api.dto.SMSSendDTO;
import com.huboot.share.notify_service.enums.SMSSendStatusEnum;
import com.huboot.share.notify_service.enums.SMSTaskStatusEnum;
import com.huboot.share.notify_service.enums.SMSTaskTypeEnum;
import com.xiehua.notify.dto.SMSLogPagerDTO;
import com.xiehua.notify.entity.SMSLog;
import com.xiehua.notify.entity.SMSTask;
import com.xiehua.notify.repository.ISMSTaskRepository;
import com.xiehua.notify.service.SMSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@Slf4j
@Service
public class SMSServiceImpl implements SMSService {

    @Autowired
    private ISMSTaskRepository taskRepository;
    @Autowired
    private SMSAsyncTaskService asyncTaskService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String sendSMS(SMSSendDTO sendDTO, SMSTaskTypeEnum typeEnum) {
        if(StringUtils.isEmpty(sendDTO.getSign())) {
            throw new BizException("短信签名不能为空");
        }
        if(StringUtils.isEmpty(sendDTO.getContent())) {
            throw new BizException("短信内容不能为空");
        }
        if(CollectionUtils.isEmpty(sendDTO.getPhoneList())) {
            throw new BizException("短信联系人不能为空");
        }
        SMSTask task = new SMSTask();
        task.setCreateTime(LocalDateTime.now());
        task.setStatus(SMSTaskStatusEnum.sending.name());
        task.setContent(sendDTO.getContent());
        //task.setSign(sendDTO.getSign().getShowName());
        task.setType(typeEnum.name());
        task.setRemark(sendDTO.getRemark());
        task.setSuccessNum(0l);
        task.setFailureNum(0l);
        task.setPhones(JsonUtil.buildNormalMapper().toJson(sendDTO.getPhoneList()));
        task.setCode(sendDTO.getCode());
        taskRepository.save(task);
        asyncTaskService.sending(task.getId());
        return task.getId();
    }

    @Override
    public Map smsLogPager(Integer page, Integer limit, String taskId, String phone, String sendStatus, String code) {
        Criteria criteria = new Criteria();
        if(!StringUtils.isEmpty(taskId)) {
            criteria.and("taskId").is(taskId);
        }
        if(!StringUtils.isEmpty(phone)) {
            criteria.and("phone").is(phone);
        }
        if(!StringUtils.isEmpty(sendStatus)) {
            criteria.and("status").is(sendStatus);
        }
        if(!StringUtils.isEmpty(code)) {
            criteria.and("code").is(code);
        }
        if(page < 1) {
            page = 1;
        }
        Pageable pageRequest = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createTime", "phone"));
        Query query = Query.query(criteria).with(pageRequest);
        long count = mongoTemplate.count(query, SMSLog.class);
        List<SMSLog> list = mongoTemplate.find(query, SMSLog.class);
        List<SMSLogPagerDTO> pagerList = new ArrayList<>();
        for(SMSLog log : list) {
            SMSLogPagerDTO pagerDTO = new SMSLogPagerDTO();
            pagerDTO.setSendTime(LocalDateTimeUtils.formatTimeNormal(log.getCreateTime()));
            pagerDTO.setPhone(log.getPhone());
            pagerDTO.setTaskId(log.getTaskId());
            pagerDTO.setStatus(SMSSendStatusEnum.valueOf(log.getStatus()).getShowName());
            Optional<SMSTask> optional = taskRepository.findById(log.getTaskId());
            if(optional.isPresent()) {
                SMSTask task = optional.get();
                pagerDTO.setContent(task.getSign() + task.getContent());
            }
            pagerList.add(pagerDTO);
        }
        return pageMap(count, pagerList);
    }

    private Map pageMap(long count, List list) {
        Map<String, Object> map = new HashMap<>();
        map.put("count", count);
        map.put("data", list);
        map.put("code", "0");
        map.put("msg", "");
        return map;
    }
}
