package com.huboot.share.user_service.api.fallback;

import com.huboot.share.user_service.api.dto.QrcodeCreateDTO;
import com.huboot.share.user_service.api.feign.MiniAppFeignCilent;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Administrator on 2018/9/10 0010.
 */
@Component
public class MiniAppFallback implements MiniAppFeignCilent {

    @Override
    public String createQrcode(@RequestBody QrcodeCreateDTO createDTO) {
        return null;
    }

    @Override
    public String createPathQrcode(@RequestBody QrcodeCreateDTO createDTO) {
        return null;
    }
}
