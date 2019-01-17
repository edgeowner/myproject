package com.huboot.share.user_service.api.feign;

import com.huboot.share.common.feign.FeignCilentConfig;
import com.huboot.share.user_service.api.dto.WxmpSendMessageDTO;
import com.huboot.share.user_service.api.fallback.WxmpFeignFallback;
import com.huboot.share.common.constant.ServiceName;
import com.huboot.share.user_service.api.dto.QrcodeCreateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Administrator on 2018/9/10 0010.
 */
@FeignClient(name = ServiceName.USER_SERVICE, configuration = FeignCilentConfig.class, fallback = WxmpFeignFallback.class)
public interface WxmpFeignCilent {

    @PostMapping("/inner/user/wxmp/send_message")
    void sendMessage(@RequestBody WxmpSendMessageDTO messageDTO);

    @PostMapping("/inner/user/wxmp/create_qrcode")
    String createQrcode(@RequestBody QrcodeCreateDTO createDTO);
}
