package com.xiehua.notify.controller.inner;

import com.xiehua.notify.service.SMSService;
import com.xiehua.share.notify_service.api.dto.SMSSendDTO;
import com.xiehua.share.notify_service.api.feign.SMSFeignClient;
import com.xiehua.share.notify_service.enums.SMSTaskTypeEnum;
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
public class InnerSMSController implements SMSFeignClient {

    @Autowired
    private SMSService SMSservice;

    @Override
    @ApiOperation(response = String.class, value = "发送通知类型短信")
    public String sendNoticeSMS(@RequestBody SMSSendDTO sendDTO) {
        return SMSservice.sendSMS(sendDTO, SMSTaskTypeEnum.notice);
    }

    @Override
    @ApiOperation(response = String.class, value = "发送营销类型短信")
    public String sendMarketingSMS(@RequestBody SMSSendDTO sendDTO) {
        return SMSservice.sendSMS(sendDTO, SMSTaskTypeEnum.marketing);
    }

}
