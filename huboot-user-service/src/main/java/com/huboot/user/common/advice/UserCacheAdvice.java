package com.huboot.user.common.advice;

import com.huboot.user.common.cache.UserCachePutData;
import com.huboot.user.user.entity.UserEntity;
import com.huboot.user.user.entity.UserPersonalEntity;
import com.huboot.user.permission.entity.PermissionEntity;
import com.huboot.user.user.entity.UserEmployeeEntity;
import com.huboot.user.user.entity.UserRoleRelationEntity;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Aspect
public class UserCacheAdvice {

    @Autowired
    private UserCachePutData userCachePutData;

    /**
     * 用户信息修改后，清空缓存信息
     * https://my.oschina.net/itblog/blog/211693
     *
     * @param param
     */
    @After(value = "(execution(* com.huboot.user.user.repository.IUserRepository.create*(..)) " +
            "|| execution(* com.huboot.user.user.repository.IUserRepository.save*(..)) " +
            "|| execution(* com.huboot.user.user.repository.IUserRepository.modify*(..)) " +
            "|| execution(* com.huboot.user.user.repository.IUserRepository.remove*(..)) " +
            "|| execution(* com.huboot.user.user.repository.IUserRepository.delete*(..)) )&& args(param)", argNames = "param")
    public void afterAdviceIUserRepository(Object param) {
        if (param instanceof UserEntity) {
            Long userId = ((UserEntity) param).getId();
            userCachePutData.clearUserRoleCache(userId);
        } else if (param instanceof List) {
            List<UserEntity> userEntityList = (List<UserEntity>) param;
            userEntityList.forEach(userEntity -> {
                userCachePutData.clearUserRoleCache(userEntity.getId());
            });
        } else if (param instanceof Long) {
            Long userId = (Long) param;
            userCachePutData.clearUserRoleCache(userId);
        }
    }

    /**
     * 用户-个人信息修改后，清空缓存信息
     *
     * @param param
     */
    @After(value = "(execution(* com.huboot.user.user.repository.IUserPersonalRepository.create*(..)) " +
            "|| execution(* com.huboot.user.user.repository.IUserPersonalRepository.save*(..)) " +
            "|| execution(* com.huboot.user.user.repository.IUserPersonalRepository.modify*(..)) " +
            "|| execution(* com.huboot.user.user.repository.IUserPersonalRepository.remove*(..)) " +
            "|| execution(* com.huboot.user.user.repository.IUserPersonalRepository.delete*(..))) && args(param)", argNames = "param")
    public void afterAdviceIUserPersonalRepository(Object param) {
        if (param instanceof UserPersonalEntity) {
            Long userId = ((UserPersonalEntity) param).getUserId();
            userCachePutData.clearUserRoleCache(userId);
        } else if (param instanceof List) {
            List<UserPersonalEntity> userPersonalEntities = (List<UserPersonalEntity>) param;
            userPersonalEntities.forEach(userPersonalEntity -> {
                userCachePutData.clearUserRoleCache(userPersonalEntity.getUserId());
            });
        } /*else if (param instanceof Long) {
            Long userPersonalId = (Long) param;
            //查找到userId，再清空
            updateUserRoleCache.clearUserRoleCache(userId);
        }*/
    }

    /**
     * 用户-员工信息修改后，清空缓存信息
     *
     * @param param
     */
    @After(value = "(execution(* com.huboot.user.user.repository.IUserEmployeeRepository.create*(..)) " +
            "|| execution(* com.huboot.user.user.repository.IUserEmployeeRepository.save*(..)) " +
            "|| execution(* com.huboot.user.user.repository.IUserEmployeeRepository.modify*(..)) " +
            "|| execution(* com.huboot.user.user.repository.IUserEmployeeRepository.remove*(..)) " +
            "|| execution(* com.huboot.user.user.repository.IUserEmployeeRepository.delete*(..))) && args(param)", argNames = "param")
    public void afterAdviceIUserEmployeeRepository(Object param) {
        if (param instanceof UserEmployeeEntity) {
            Long userId = ((UserEmployeeEntity) param).getUserId();
            userCachePutData.clearUserRoleCache(userId);
        } else if (param instanceof List) {
            List<UserEmployeeEntity> userEmployeeEntityList = (List<UserEmployeeEntity>) param;
            userEmployeeEntityList.forEach(userEmployeeEntity -> {
                userCachePutData.clearUserRoleCache(userEmployeeEntity.getUserId());
            });
        } /*else if (param instanceof Long) {
            Long userPersonalId = (Long) param;
            //查找到userId，再清空
            updateUserRoleCache.clearUserRoleCache(userId);
        }*/
    }

    /**
     * 用户角色关系信息修改后，重置缓存信息-网关使用
     * https://my.oschina.net/itblog/blog/211693
     *
     * @param param
     */
    @After(value = "(execution(* com.huboot.user.user.repository.IUserRoleRelationRepository.create*(..)) " +
            "|| execution(* com.huboot.user.user.repository.IUserRoleRelationRepository.save*(..)) " +
            "|| execution(* com.huboot.user.user.repository.IUserRoleRelationRepository.modify*(..)) " +
            "|| execution(* com.huboot.user.user.repository.IUserRoleRelationRepository.remove*(..)) " +
            "|| execution(* com.huboot.user.user.repository.IUserRoleRelationRepository.delete*(..)) )&& args(param)", argNames = "param")
    public void afterAdviceIUserRoleRelationRepository(Object param) {
        if (param instanceof UserRoleRelationEntity) {
            Long userId = ((UserRoleRelationEntity) param).getUserId();
            userCachePutData.updateUserRoleCache(userId);
        } else if (param instanceof List) {
            List<UserRoleRelationEntity> userRoleRelationEntityList = (List<UserRoleRelationEntity>) param;
            userRoleRelationEntityList.forEach(userRoleRelationEntity -> {
                userCachePutData.updateUserRoleCache(userRoleRelationEntity.getUserId());
            });
        }
        //系统暂时不删除关系，顾暂时不考虑，知晓，知晓-坑需要通过id置换userId
        /*else if (param instanceof Long) {
            Long userId = (Long) param;
            userCachePutData.updateUserRoleCache(userId);
        }*/
    }

    /**
     * 资源/权限信息修改后，重置缓存信息-网关使用
     * https://my.oschina.net/itblog/blog/211693
     *
     * @param param
     */
    @After(value = "(execution(* com.huboot.user.permission.repository.IPermissionRepository.create*(..)) " +
            "|| execution(* com.huboot.user.permission.repository.IPermissionRepository.save*(..)) " +
            "|| execution(* com.huboot.user.permission.repository.IPermissionRepository.modify*(..)) " +
            "|| execution(* com.huboot.user.permission.repository.IPermissionRepository.remove*(..)) " +
            "|| execution(* com.huboot.user.permission.repository.IPermissionRepository.delete*(..)) )&& args(param)", argNames = "param")
    public void afterAdviceIPermissionRepository(Object param) {
        if (param instanceof PermissionEntity) {
            Long roleId = ((PermissionEntity) param).getRoleId();
            userCachePutData.updateUrlPermission(roleId);
        } else if (param instanceof List) {
            List<PermissionEntity> userRoleRelationEntityList = (List<PermissionEntity>) param;
            userRoleRelationEntityList.forEach(permissionEntity -> {
                userCachePutData.updateUrlPermission(permissionEntity.getRoleId());
            });
        }
        //系统暂时不删除关系，顾暂时不考虑，知晓-坑需要通过id置换roleId
        /*else if (param instanceof Long) {
            Long roleId = (Long) param;
            userCachePutData.updateUrlPermission(roleId);
        }*/
    }
}
