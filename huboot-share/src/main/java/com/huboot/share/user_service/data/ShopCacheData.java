package com.huboot.share.user_service.data;

import com.huboot.share.user_service.api.dto.ShopDetaiInfo;
import com.huboot.share.user_service.data.proxy.CacheDataProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/9/7 0007.
 */
@Component
public class ShopCacheData {

    @Autowired
    private CacheDataProxy proxy;
    @Autowired
    private AreaCacheData areaCacheData;

    /**
     * 获取地区信息
     *
     * @param id
     * @return
     */
    public ShopDetaiInfo getShopById(Long id) {
        return proxy.getShopInfo(id);
    }
}
