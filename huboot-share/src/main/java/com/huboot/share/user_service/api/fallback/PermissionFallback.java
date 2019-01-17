package com.huboot.share.user_service.api.fallback;

import com.huboot.commons.sso.PermissionResourcesDTO;
import com.huboot.share.user_service.api.feign.PermissionFeignClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/7 0007.
 */
@Component
public class PermissionFallback implements PermissionFeignClient {
    @Override
    public List<PermissionResourcesDTO> getRolePermission(Long roleId) {
        return new ArrayList<>();
    }
}
