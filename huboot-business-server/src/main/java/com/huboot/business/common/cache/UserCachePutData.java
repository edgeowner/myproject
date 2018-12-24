package com.huboot.business.common.cache;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserCachePutData {

    /*@Autowired
    private IUserService userService;
    @Autowired
    private IPermissionService permissionService;

    *//**
     * 网关使用
     *
     * @param userId
     * @return
     *//*
    @CachePut(value = SSORedisHashName.USER_PERMISSION, key = "#userId", cacheManager = "redisCacheManager", unless = "#result == null")
    public List<String> updateUserRoleCache(Long userId) {
        return userService.getUserRole(userId);
    }

    *//**
     * 网关使用
     *
     * @param roleId
     * @return
     *//*
    @CachePut(value = SSORedisHashName.URL_PERMISSION, key = "#roleId", cacheManager = "redisCacheManager", unless = "#result == null")
    public List<PermissionResourcesDTO> updateUrlPermission(Long roleId) {
        return permissionService.getRolePermission(roleId);
    }

    @CacheEvict(value = RedisHashName.USER_DETAIL_INFO, key = "#userId")
    public void clearUserRoleCache(Long userId) {
    }*/
}
