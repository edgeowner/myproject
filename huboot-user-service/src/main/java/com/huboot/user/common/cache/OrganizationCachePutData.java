package com.huboot.user.common.cache;

import com.huboot.share.common.constant.RedisHashName;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
public class OrganizationCachePutData {

    @CacheEvict(value = RedisHashName.COMPANY_INFO, key = "#id")
    public void clearCompanyInfoCache(Long id) {
    }
    @CacheEvict(value = RedisHashName.SHOP_INFO, key = "#id")
    public void clearShopInfoCache(Long id) {
    }
}
