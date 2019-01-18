package com.huboot.user.user.controller.inner;

import com.huboot.share.user_service.api.dto.UserDetailInfo;
import com.huboot.share.user_service.api.feign.UserFeignClient;
import com.huboot.user.user.service.IUserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "内部端-用户服务-用户基础信息表 API")
@RestController
public class InnerUserController implements UserFeignClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserService userService;

    @Override
    public List<Long> getUserIdListCondition(@RequestParam(value = "name", required = false) String name,
                                             @RequestParam(value = "phone", required = false) String phone,
                                             @RequestParam(value = "idcard", required = false) String idcard) {
        return userService.getUserIdListCondition(name, phone, idcard);
    }

    @Override
    public UserDetailInfo getUserDetailInfo(@PathVariable("userId") Long userId) {
        return userService.findForInner(userId);
    }

    @Override
    public List<String> getUserRole(@PathVariable("userId") Long userId) {
        return userService.getUserRole(userId);
    }

}
