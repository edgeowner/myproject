package com.huboot.share.notify_service.api.feign;

import com.huboot.share.common.constant.ServiceName;
import com.huboot.share.common.feign.FeignCilentConfig;
import com.huboot.share.notify_service.api.dto.SMSSendDTO;
import com.huboot.share.notify_service.api.fallback.SMSFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@FeignClient(name = ServiceName.NOTIFY_SERVICE, configuration = FeignCilentConfig.class, fallback = SMSFeignClientFallback.class)
public interface SMSFeignClient {

    /**
     * 发送通知类型短信
     * @param sendDTO
     * @return
     */
    @PostMapping("/inner/notify/sms/send/notice")
    String sendNoticeSMS(@RequestBody SMSSendDTO sendDTO);

    /**
     * 发送营销类型短信
     * @param sendDTO
     * @return
     */
    @PostMapping("/inner/notify/sms/send/marketing")
    String sendMarketingSMS(@RequestBody SMSSendDTO sendDTO);
}
