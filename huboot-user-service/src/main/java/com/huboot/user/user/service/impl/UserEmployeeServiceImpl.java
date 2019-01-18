package com.huboot.user.user.service.impl;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.jpa.ConditionMap;
import com.huboot.commons.jpa.QueryCondition;
import com.huboot.commons.page.ShowPageImpl;
import com.huboot.share.common.constant.BizConstant;
import com.huboot.share.user_service.data.UserCacheData;
import com.huboot.user.permission.entity.PermissionEntity;
import com.huboot.user.permission.service.IPermissionService;
import com.huboot.user.role.dto.RoleDetailResDTO;
import com.huboot.user.role.entity.RoleEntity;
import com.huboot.user.role.repository.IRoleRepository;
import com.huboot.user.role.service.IRoleService;
import com.huboot.user.user.dto.zkshop.*;
import com.huboot.user.user.entity.UserEmployeeEntity;
import com.huboot.user.user.entity.UserEntity;
import com.huboot.user.user.entity.UserRoleRelationEntity;
import com.huboot.user.user.repository.IUserEmployeeRepository;
import com.huboot.user.user.service.IUserEmployeeService;
import com.huboot.user.user.repository.IUserRepository;
import com.huboot.user.user.repository.IUserRoleRelationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 用户服务-企业员工表ServiceImpl
 */
@Slf4j
@Service("userEmployeeServiceImpl")
public class UserEmployeeServiceImpl implements IUserEmployeeService {

    @Autowired
    private IUserEmployeeRepository userEmployeeRepository;
    @Autowired
    private UserCacheData userCacheData;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IUserRoleRelationRepository userRoleRelationRepository;

    @Transactional
    @Override
    public void create(ZkshopUserEmployeeCreateReqDTO createReqDTO) throws BizException {
        UserEmployeeEntity entity = new UserEmployeeEntity();
        BeanUtils.copyProperties(createReqDTO, entity);
        userEmployeeRepository.create(entity);
    }

    @Transactional
    @Override
    public void modify(ZkshopUserEmployeeModifyReqDTO modifyReqDTO) throws BizException {
        UserEmployeeEntity entity = userEmployeeRepository.find(modifyReqDTO.getId());
        UserEntity userEntity = entity.getUserEntity();
        userEntity.setName(modifyReqDTO.getName());
        userRepository.modify(userEntity);
        RoleDetailResDTO roleDetailResDTO = roleService.findMenuForZkshopByUserId(entity.getUserId());
        RoleDetailResDTO roleDetailResDTO1 = roleService.find(modifyReqDTO.getRoleId());
        if (roleDetailResDTO.getName().equals(BizConstant.ZKSHOP_MANAGER_ROLE_NAME) && !roleDetailResDTO1.getName().equals(BizConstant.ZKSHOP_MANAGER_ROLE_NAME)) {
            throw new BizException("非法访问");
        }
        UserRoleRelationEntity userRoleRelationEntity = userRoleRelationRepository.findByUserIdAndRoleId(entity.getUserId(), roleDetailResDTO.getId());
        userRoleRelationEntity.setRoleId(modifyReqDTO.getRoleId());
        userRoleRelationRepository.modify(userRoleRelationEntity);
    }

    @Transactional
    @Override
    public void delete(Long id) throws BizException {
        userEmployeeRepository.remove(id);
    }

    @Override
    public ZkshopUserEmployeeDetailResDTO find(Long id) throws BizException {
        UserEmployeeEntity entity = userEmployeeRepository.find(id);
        if (!entity.getOrganizationId().equals(userCacheData.getCurrentUserEmployeeOrganizationId())) {
            throw new BizException("非法访问");
        }
        ZkshopUserEmployeeDetailResDTO dto = new ZkshopUserEmployeeDetailResDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setName(entity.getUserEntity().getName());
        dto.setPhone(entity.getUserEntity().getPhone());
        RoleDetailResDTO roleDetailResDTO = roleService.findMenuForZkshopByUserId(entity.getUserId());
        if (roleDetailResDTO != null) {
            if (!CollectionUtils.isEmpty(roleDetailResDTO.getPermission())) {
                dto.setRoleId(roleDetailResDTO.getId());
                dto.setRoleName(roleDetailResDTO.getName());
            }
        }
        Page<RoleEntity> page = roleRepository.findPage(QueryCondition.from(RoleEntity.class).where(list -> {
            list.add(ConditionMap.eq("organizationId", userCacheData.getCurrentUserEmployeeOrganizationId()));
            list.add(ConditionMap.ne("name", BizConstant.ZKSHOP_MANAGER_ROLE_NAME));
        }).sort(Sort.by("id").descending()).limit(1, Integer.MAX_VALUE));
        page.getContent().forEach(roleEntity -> {
            ZkshopUserEmployeeDetailResDTO.Role role =  dto.new Role();
            role.setId(roleEntity.getId());
            role.setName(roleEntity.getName());
            dto.getRoleList().add(role);
        });
        return dto;
    }

    @Override
    public ShowPageImpl<ZkshopUserEmployeePageResDTO> findPage(ZkshopUserEmployeeQueryReqDTO queryReqDTO) throws BizException {

        Page<UserEmployeeEntity> page = userEmployeeRepository.findPage(QueryCondition.from(UserEmployeeEntity.class).where(list -> {
            list.add(ConditionMap.eq("organizationId", userCacheData.getCurrentUserEmployeeOrganizationId()));
        }).sort(Sort.by("id").ascending()).limit(queryReqDTO.getPage(), queryReqDTO.getSize()));

        return new ShowPageImpl<>(page).map(entity -> {
            ZkshopUserEmployeePageResDTO dto = new ZkshopUserEmployeePageResDTO();
            BeanUtils.copyProperties(entity, dto);
            dto.setName(entity.getUserEntity().getName());
            dto.setPhone(entity.getUserEntity().getPhone());
            RoleDetailResDTO roleDetailResDTO = roleService.findMenuForZkshopByUserId(entity.getUserId());
            if (roleDetailResDTO != null) {
                if (!CollectionUtils.isEmpty(roleDetailResDTO.getPermission())) {
                    dto.setRoleName(roleDetailResDTO.getName());
                    dto.setColour(roleDetailResDTO.getColour());
                    PermissionEntity permissionEntity = permissionService.findMenuByRoleId(roleDetailResDTO.getId());
                    dto.setPermission(permissionEntity.getResourcesUrl());
                }
            }
            return dto;
        });
    }
}
