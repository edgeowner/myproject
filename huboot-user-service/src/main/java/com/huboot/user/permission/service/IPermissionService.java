package com.huboot.user.permission.service;

import com.huboot.commons.sso.PermissionResourcesDTO;
import com.huboot.share.user_service.enums.PermissionTypeEnum;
import com.huboot.user.permission.entity.PermissionEntity;
import com.huboot.user.user.dto.admin.PermissionCreateReqDTO;

import java.util.List;
import java.util.Map;

/**
 * 用户服务-权限表Service
 */
public interface IPermissionService {

    Map<String, List<PermissionResourcesDTO>> findByType(PermissionTypeEnum permissionTypeEnum);

    List<PermissionResourcesDTO> getRolePermission(Long roleId);

    void modifyResourcesUrlByRoleIdForMenu(Long roleId, List<String> resourcesUrl);

    void createForApi(Long roleId);
    void createForAllApi(PermissionCreateReqDTO wycShopRoleCreateReqDTO);

    void createForMenu(Long roleId, List<String> resourcesUrl);

    PermissionEntity findMenuByRoleId(Long roleId);
    PermissionEntity findApiByRoleId(Long roleId);
}
