package com.xiehua.notify.controller;

import com.xiehua.notify.service.SMSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@Api(tags = "发送短信 API")
@RestController
public class SMSController {

    @Autowired
    private SMSService SMSservice;


    @GetMapping("/admin/sms_log_pager")
    @ApiOperation(response = String.class, value = "短信发送记录")
    public Map smsLogPager(@RequestParam(name = "page", required = true, defaultValue = "1")Integer page,
                           @RequestParam(name = "limit", required = true, defaultValue = "20")Integer limit,
                           @RequestParam(name = "taskId", required = false)String taskId,
                           @RequestParam(name = "phone", required = false)String phone,
                           @RequestParam(name = "sendStatus", required = false)String sendStatus,
                           @RequestParam(name = "code", required = false)String code) {
        return SMSservice.smsLogPager(page, limit, taskId, phone, sendStatus, code);
    }
}
