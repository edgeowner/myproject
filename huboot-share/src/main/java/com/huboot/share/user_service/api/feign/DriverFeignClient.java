package com.huboot.share.user_service.api.feign;

import com.huboot.share.common.feign.FeignCilentConfig;
import com.huboot.share.common.constant.ServiceName;
import com.huboot.share.user_service.api.dto.DriverDTO;
import com.huboot.share.user_service.api.fallback.DriverFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by Administrator on 2018/9/7 0007.
 */
@FeignClient(name = ServiceName.USER_SERVICE, configuration = FeignCilentConfig.class, fallback = DriverFallback.class)
public interface DriverFeignClient {

    @GetMapping("/inner/user/driver/find_by_car/{carArchivesId}")
    public DriverDTO findByCar(@PathVariable("carArchivesId") Long carArchivesId);

}
