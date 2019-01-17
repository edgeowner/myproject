package com.huboot.share.user_service.api.fallback;

import com.huboot.share.user_service.api.dto.ShopDetaiInfo;
import com.huboot.share.user_service.api.feign.ShopFeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by Administrator on 2018/9/10 0010.
 */
@Component
public class ShopFallback implements ShopFeignClient {

    @Override
    public ShopDetaiInfo get(@PathVariable("id") Long id) {
        return null;
    }

    @Override
    public ShopDetaiInfo findByShopSn(@PathVariable("shopSn") String shopSn) {
        return null;
    }
}
