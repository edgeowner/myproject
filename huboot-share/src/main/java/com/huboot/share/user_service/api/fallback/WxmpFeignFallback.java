package com.huboot.share.user_service.api.fallback;

import com.huboot.commons.utils.JsonUtil;
import com.huboot.share.user_service.api.dto.QrcodeCreateDTO;
import com.huboot.share.user_service.api.dto.WxmpSendMessageDTO;
import com.huboot.share.user_service.api.feign.WxmpFeignCilent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Administrator on 2018/12/6 0006.
 */
@Slf4j
@Component
public class WxmpFeignFallback implements WxmpFeignCilent {

    @Override
    public void sendMessage(@RequestBody WxmpSendMessageDTO messageDTO) {
        log.error("WxmpFeignCilent.sendMessage Fallback ，参数={}", JsonUtil.buildNormalMapper().toJson(messageDTO));
    }

    @Override
    public String createQrcode(QrcodeCreateDTO createDTO) {
        log.error("WxmpFeignCilent.createQrcode Fallback ，参数={}", JsonUtil.buildNormalMapper().toJson(createDTO));
        return null;
    }
}
