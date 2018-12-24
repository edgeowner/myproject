package com.huboot.business.common.cache;


import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Aspect
public class UserCacheAdvice {

    @Autowired
    private UserCachePutData updateUserRoleCache;

    /**
     * 用户信息修改后，清空缓存信息
     * https://my.oschina.net/itblog/blog/211693
     *
     * @param param
     */
   /* @After(value = "(execution(* com.xiehua.user.user.repository.IUserRepository.create*(..)) " +
            "|| execution(* com.xiehua.user.user.repository.IUserRepository.save*(..)) " +
            "|| execution(* com.xiehua.user.user.repository.IUserRepository.modify*(..)) " +
            "|| execution(* com.xiehua.user.user.repository.IUserRepository.remove*(..)) " +
            "|| execution(* com.xiehua.user.user.repository.IUserRepository.delete*(..)) )&& args(param)", argNames = "param")
    public void afterAdviceIUserRepository(Object param) {
        if (param instanceof UserEntity) {
            Long userId = ((UserEntity) param).getId();
            updateUserRoleCache.clearUserRoleCache(userId);
        } else if (param instanceof List) {
            List<UserEntity> userEntityList = (List<UserEntity>) param;
            userEntityList.forEach(userEntity -> {
                updateUserRoleCache.clearUserRoleCache(userEntity.getId());
            });
        } else if (param instanceof Long) {
            Long userId = (Long) param;
            updateUserRoleCache.clearUserRoleCache(userId);
        }
    }*/

    /**
     * 用户-个人信息修改后，清空缓存信息
     *
     * @param param
     */
   /* @After(value = "(execution(* com.xiehua.user.user.repository.IUserPersonalRepository.create*(..)) " +
            "|| execution(* com.xiehua.user.user.repository.IUserPersonalRepository.save*(..)) " +
            "|| execution(* com.xiehua.user.user.repository.IUserPersonalRepository.modify*(..)) " +
            "|| execution(* com.xiehua.user.user.repository.IUserPersonalRepository.remove*(..)) " +
            "|| execution(* com.xiehua.user.user.repository.IUserPersonalRepository.delete*(..))) && args(param)", argNames = "param")
    public void afterAdviceIUserPersonalRepository(Object param) {
        if (param instanceof UserPersonalEntity) {
            Long userId = ((UserPersonalEntity) param).getUserId();
            updateUserRoleCache.clearUserRoleCache(userId);
        } else if (param instanceof List) {
            List<UserPersonalEntity> userPersonalEntities = (List<UserPersonalEntity>) param;
            userPersonalEntities.forEach(userPersonalEntity -> {
                updateUserRoleCache.clearUserRoleCache(userPersonalEntity.getUserId());
            });
        } *//*else if (param instanceof Long) {
            Long userPersonalId = (Long) param;
            //查找到userId，再清空
            updateUserRoleCache.clearUserRoleCache(userId);
        }*//*
    }*/
}
