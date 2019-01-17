package com.huboot.share.user_service.api.fallback;

import com.huboot.share.user_service.api.dto.CompanyDetailInfo;
import com.huboot.share.user_service.api.feign.CompanyFeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by Administrator on 2018/9/10 0010.
 */
@Component
public class CompanyFallback implements CompanyFeignClient {

    @Override
    public CompanyDetailInfo get(@PathVariable("id") Long id) {
        return null;
    }

    @Override
    public List<CompanyDetailInfo> all() {
        return null;
    }
}
