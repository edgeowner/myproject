package com.huboot.share.user_service.data;

import com.huboot.share.user_service.api.dto.CompanyDetailInfo;
import com.huboot.share.user_service.data.proxy.CacheDataProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/9/7 0007.
 */
@Component
public class CompanyCacheData {

    @Autowired
    private CacheDataProxy proxy;

    /**
     * 获取地区信息
     * @param id
     * @return
     */
    public CompanyDetailInfo getById(Long id) {
        return proxy.getCompanyInfo(id);
    }
}
