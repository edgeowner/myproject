package com.huboot.user.permission.repository;

import com.huboot.commons.jpa.IBaseRepository;
import com.huboot.share.user_service.enums.PermissionTypeEnum;
import org.springframework.stereotype.Repository;
import com.huboot.user.permission.entity.PermissionEntity;

import java.util.List;

/**
*用户服务-权限表Repository
*/
@Repository("permissionRepository")
public interface IPermissionRepository extends IBaseRepository<PermissionEntity> {

    List<PermissionEntity> findByRoleId(Long roleId);
    List<PermissionEntity> findByRoleIdAndType(Long roleId, PermissionTypeEnum permissionTypeEnum);
    List<PermissionEntity> findByType(PermissionTypeEnum permissionTypeEnum);
}
