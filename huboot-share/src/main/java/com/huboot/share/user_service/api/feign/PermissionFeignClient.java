package com.huboot.share.user_service.api.feign;

import com.huboot.commons.sso.PermissionResourcesDTO;
import com.huboot.share.common.feign.FeignCilentConfig;
import com.huboot.share.common.constant.ServiceName;
import com.huboot.share.user_service.api.fallback.PermissionFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by Administrator on 2018/9/7 0007.
 */
@FeignClient(name = ServiceName.USER_SERVICE, configuration = FeignCilentConfig.class, fallback = PermissionFallback.class)
public interface PermissionFeignClient {

    @GetMapping("/inner/permission/permission/get_role_permission/{roleId}")
    public List<PermissionResourcesDTO> getRolePermission(@PathVariable("roleId") Long roleId);
}
