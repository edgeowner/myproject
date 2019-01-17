package com.huboot.share.user_service.api.fallback;

import com.huboot.share.user_service.api.dto.AreaDTO;
import com.huboot.share.user_service.api.feign.AreaFeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by Administrator on 2018/9/8 0008.
 */
@Component
public class AreaFallback implements AreaFeignClient {

    @Override
    public AreaDTO get(@PathVariable("id") Long id) {
        return null;
    }
}
