package com.huboot.user.common.cache;

import com.huboot.commons.sso.PermissionResourcesDTO;
import com.huboot.commons.sso.SSORedisHashName;
import com.huboot.share.common.constant.RedisHashName;
import com.huboot.user.permission.service.IPermissionService;
import com.huboot.user.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserCachePutData {

    @Autowired
    private IUserService userService;
    @Autowired
    private IPermissionService permissionService;

    /**
     * 网关使用
     *
     * @param userId
     * @return
     */
    @CachePut(value = SSORedisHashName.USER_PERMISSION, key = "#userId", cacheManager = "redisCacheManager", unless = "#result == null")
    public List<String> updateUserRoleCache(Long userId) {
        return userService.getUserRole(userId);
    }

    /**
     * 网关使用
     *
     * @param roleId
     * @return
     */
    @CachePut(value = SSORedisHashName.URL_PERMISSION, key = "#roleId", cacheManager = "redisCacheManager", unless = "#result == null")
    public List<PermissionResourcesDTO> updateUrlPermission(Long roleId) {
        return permissionService.getRolePermission(roleId);
    }

    @CacheEvict(value = RedisHashName.USER_DETAIL_INFO, key = "#userId")
    public void clearUserRoleCache(Long userId) {
    }

    @CacheEvict(value = RedisHashName.USER_SERVICE_AUTH_LOGIN_CODE_ZKUSER, key = "#phone")
    public void clearCodeForZkUser(String phone) {
    }

    @CacheEvict(value = RedisHashName.USER_SERVICE_AUTH_LOGIN_CODE_ZKSHOP, key = "#phone")
    public void clearCodeForZkShop(String phone) {
    }
}
