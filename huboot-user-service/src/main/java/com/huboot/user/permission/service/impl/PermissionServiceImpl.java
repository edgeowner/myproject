package com.huboot.user.permission.service.impl;

import com.huboot.commons.sso.PermissionResourcesDTO;
import com.huboot.commons.sso.SSOConstant;
import com.huboot.share.common.constant.BizConstant;
import com.huboot.share.user_service.enums.PermissionHttpMethodEnum;
import com.huboot.share.user_service.enums.PermissionTypeEnum;
import com.huboot.user.common.cache.UserCachePutData;
import com.huboot.user.permission.repository.IPermissionRepository;
import com.huboot.user.permission.entity.PermissionEntity;
import com.huboot.user.permission.service.IPermissionService;
import com.huboot.user.user.dto.admin.PermissionCreateReqDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户服务-权限表ServiceImpl
 */
@Service("permissionServiceImpl")
public class PermissionServiceImpl implements IPermissionService {

    private Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Autowired
    private IPermissionRepository permissionRepository;
    @Autowired
    private UserCachePutData userCachePutData;

    @Override
    public Map<String, List<PermissionResourcesDTO>> findByType(PermissionTypeEnum permissionTypeEnum) {
        Map<String, List<PermissionResourcesDTO>> listMap = new HashMap<>();
        //这里查询到的都是
        List<PermissionEntity> permissionEntities = permissionRepository.findByType(permissionTypeEnum);
        if (!CollectionUtils.isEmpty(permissionEntities)) {
            listMap = permissionEntities.stream().collect(Collectors.groupingBy(permissionEntity -> String.valueOf(permissionEntity.getRoleId()), Collectors.mapping(permissionEntity -> {
                PermissionResourcesDTO dto = new PermissionResourcesDTO();
                dto.setUrl(permissionEntity.getResourcesUrl());
                dto.setMethod(permissionEntity.getResourcesMethod().stream().map(PermissionHttpMethodEnum::name).collect(Collectors.toList()));
                return dto;
            }, Collectors.toList())));
        }
        return listMap;
    }

    @Override
    public List<PermissionResourcesDTO> getRolePermission(Long roleId) {
        List<PermissionEntity> permissionEntities = permissionRepository.findByRoleIdAndType(roleId, PermissionTypeEnum.API);
        List<PermissionResourcesDTO> permissionResourcesDTOS = new ArrayList<>();
        if (CollectionUtils.isEmpty(permissionEntities)) {
            return permissionResourcesDTOS;
        }
        return permissionEntities.stream().map(permissionEntity -> {
            PermissionResourcesDTO dto = new PermissionResourcesDTO();
            dto.setUrl(permissionEntity.getResourcesUrl());
            dto.setMethod(permissionEntity.getResourcesMethod().stream().map(PermissionHttpMethodEnum::name).collect(Collectors.toList()));
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void modifyResourcesUrlByRoleIdForMenu(Long roleId, List<String> resourcesUrl) {
        PermissionEntity permissionEntity = permissionRepository.findByRoleIdAndType(roleId, PermissionTypeEnum.Menu).get(0);
        permissionEntity.setResourcesUrl(resourcesUrl);
        permissionEntity.setResourcesMethod(Arrays.asList(PermissionHttpMethodEnum.ALL));
        permissionRepository.modify(permissionEntity);
    }

    @Override
    @Transactional
    public void createForApi(Long roleId) {
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setDescription("");
        PermissionEntity permissionEntity2 = permissionRepository.findByRoleIdAndType(BizConstant.WYCSHOP_ROLE_ID, PermissionTypeEnum.API).get(0);
        permissionEntity.setResourcesUrl(permissionEntity2.getResourcesUrl());
        permissionEntity.setResourcesMethod(permissionEntity2.getResourcesMethod());
        permissionEntity.setType(PermissionTypeEnum.API);
        permissionEntity.setRoleId(roleId);
        permissionRepository.create(permissionEntity);

    }

    @Override
    @Transactional
    public void createForAllApi(PermissionCreateReqDTO wycShopRoleCreateReqDTO) {
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setDescription(wycShopRoleCreateReqDTO.getDescription());
        permissionEntity.setResourcesUrl(wycShopRoleCreateReqDTO.getPermission());
        permissionEntity.setResourcesMethod(Arrays.asList(PermissionHttpMethodEnum.ALL));
        permissionEntity.setType(PermissionTypeEnum.API);
        permissionEntity.setRoleId(SSOConstant.ANONYMOUS_API_ROLE_ID);
        permissionRepository.create(permissionEntity);
    }

    @Override
    public void createForMenu(Long roleId, List<String> resourcesUrl) {
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setDescription("");
        permissionEntity.setResourcesUrl(resourcesUrl);
        permissionEntity.setResourcesMethod(Arrays.asList(PermissionHttpMethodEnum.ALL));
        permissionEntity.setType(PermissionTypeEnum.Menu);
        permissionEntity.setRoleId(roleId);
        permissionRepository.create(permissionEntity);
    }

    @Override
    public PermissionEntity findMenuByRoleId(Long roleId) {
        List<PermissionEntity> permissionEntityList = permissionRepository.findByRoleIdAndType(roleId, PermissionTypeEnum.Menu);
        if(!CollectionUtils.isEmpty(permissionEntityList)){
            return permissionEntityList.get(0);
        }
        return null;
    }

    @Override
    public PermissionEntity findApiByRoleId(Long roleId) {
        List<PermissionEntity> permissionEntityList = permissionRepository.findByRoleIdAndType(roleId, PermissionTypeEnum.API);
        if(!CollectionUtils.isEmpty(permissionEntityList)){
            return permissionEntityList.get(0);
        }
        return null;
    }
}
