package com.huboot.share.user_service.api.feign;

import com.huboot.share.common.feign.FeignCilentConfig;
import com.huboot.share.user_service.api.dto.CompanyDetailInfo;
import com.huboot.share.user_service.api.fallback.CompanyFallback;
import com.huboot.share.common.constant.ServiceName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by Administrator on 2018/9/10 0010.
 */
@FeignClient(name = ServiceName.USER_SERVICE, configuration = FeignCilentConfig.class, fallback = CompanyFallback.class)
public interface CompanyFeignClient {

    @GetMapping(value = "/inner/organization/organization_company/{id}")
    public CompanyDetailInfo get(@PathVariable("id") Long id);

    @GetMapping(value = "/inner/organization/organization_company/all")
    public List<CompanyDetailInfo> all();
}
