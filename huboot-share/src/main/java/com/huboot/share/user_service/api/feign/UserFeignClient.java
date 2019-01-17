package com.huboot.share.user_service.api.feign;

import com.huboot.share.common.constant.ServiceName;
import com.huboot.share.common.feign.FeignCilentConfig;
import com.huboot.share.user_service.api.dto.UserDetailInfo;
import com.huboot.share.user_service.api.fallback.UserFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Administrator on 2018/9/7 0007.
 */
@FeignClient(name = ServiceName.USER_SERVICE, configuration = FeignCilentConfig.class, fallback = UserFallback.class)
public interface UserFeignClient {
    /**
     * 查商家组织的用户
     * @param name
     * @param phone
     * @param idcard
     * @return
     */
    @GetMapping("/inner/user/user/get_userid_list")
    public List<Long> getUserIdListCondition(@RequestParam(value = "name", required = false) String name,
                                             @RequestParam(value = "phone", required = false) String phone,
                                             @RequestParam(value = "idcard", required = false) String idcard);


    @GetMapping("/inner/user/user/get_user_detail_info/{userId}")
    public UserDetailInfo getUserDetailInfo(@PathVariable("userId") Long userId);

    @GetMapping("/inner/user/user/get_user_role/{userId}")
    public List<String> getUserRole(@PathVariable("userId") Long userId);


}
