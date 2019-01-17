package com.huboot.share.notify_service.api.fallback;

import com.huboot.commons.component.exception.BizException;
import com.huboot.share.notify_service.api.dto.SMSSendDTO;
import com.huboot.share.notify_service.api.feign.SMSFeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@Component
public class SMSFeignClientFallback implements SMSFeignClient {

    @Override
    public String sendNoticeSMS(@RequestBody SMSSendDTO sendDTO) {
        throw new BizException("请求失败");
    }

    @Override
    public String sendMarketingSMS(@RequestBody SMSSendDTO sendDTO) {
        throw new BizException("请求失败");
    }
}
