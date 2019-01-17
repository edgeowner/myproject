package com.huboot.share.user_service.api.feign;

import com.huboot.share.common.constant.ServiceName;
import com.huboot.share.common.feign.FeignCilentConfig;
import com.huboot.share.user_service.api.dto.ShopDetaiInfo;
import com.huboot.share.user_service.api.fallback.ShopFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by Administrator on 2018/9/10 0010.
 */
@FeignClient(name = ServiceName.USER_SERVICE, configuration = FeignCilentConfig.class, fallback = ShopFallback.class)
public interface ShopFeignClient {

    @GetMapping(value = "/inner/organization/organization_shop/{id}")
    public ShopDetaiInfo get(@PathVariable("id") Long id);

    @GetMapping(value = "/inner/organization_shop/findBySn/{shopSn}")
    public ShopDetaiInfo findByShopSn(@PathVariable("shopSn") String shopSn);

}
