package com.huboot.share.user_service.api.fallback;

import com.huboot.share.user_service.api.dto.UserDetailInfo;
import com.huboot.share.user_service.api.feign.UserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/7 0007.
 */
@Slf4j
@Component
public class UserFallback implements UserFeignClient {

    @Override
    public List<Long> getUserIdListCondition(@RequestParam("name") String name, @RequestParam("phone") String phone, @RequestParam("idcard") String idcard) {
        log.error("UserFeignClient.getUserIdListCondition Fallback ，参数={},{},{}", name, phone, idcard);
        return new ArrayList<>();
    }

    @Override
    public UserDetailInfo getUserDetailInfo(@PathVariable("userId") Long userId) {
        log.error("UserFeignClient.getUserDetailInfo Fallback ，参数={}", userId);
        return null;
    }

    @Override
    public List<String> getUserRole(Long userId) {
        log.error("UserFeignClient.getUserRole Fallback ，参数={}", userId);
        return new ArrayList<>();
    }
}
