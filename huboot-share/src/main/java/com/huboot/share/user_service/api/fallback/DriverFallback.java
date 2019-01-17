package com.huboot.share.user_service.api.fallback;

import com.huboot.share.user_service.api.dto.DriverDTO;
import com.huboot.share.user_service.api.feign.DriverFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/9/7 0007.
 */
@Slf4j
@Component
public class DriverFallback implements DriverFeignClient {

    @Override
    public DriverDTO findByCar(Long carArchivesId) {
        return null;
    }
}
