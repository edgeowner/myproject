package com.huboot.user.role.service.impl;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.filter.RequestInfo;
import com.huboot.commons.jpa.ConditionMap;
import com.huboot.commons.jpa.QueryCondition;
import com.huboot.commons.page.ShowPageImpl;
import com.huboot.commons.utils.AppAssert;
import com.huboot.share.common.constant.BizConstant;
import com.huboot.share.user_service.data.UserCacheData;
import com.huboot.share.user_service.enums.OrganizationSystemEnum;
import com.huboot.share.user_service.enums.PermissionTypeEnum;
import com.huboot.user.permission.repository.IPermissionRepository;
import com.huboot.user.permission.service.IPermissionService;
import com.huboot.user.role.dto.RoleDetailResDTO;
import com.huboot.user.role.dto.RoleQueryReqDTO;
import com.huboot.user.role.dto.wycshop.WycShopRolePageResDTO;
import com.huboot.user.role.dto.wycshop.WycShopRoleQueryReqDTO;
import com.huboot.user.role.dto.zkshop.ZkshopRoleQueryReqDTO;
import com.huboot.user.role.entity.RoleEntity;
import com.huboot.user.role.repository.IRoleRepository;
import com.huboot.user.role.service.IRoleService;
import com.huboot.user.user.entity.UserEntity;
import com.huboot.user.common.constant.UserConstant;
import com.huboot.user.permission.entity.PermissionEntity;
import com.huboot.user.role.dto.RoleModifyReqDTO;
import com.huboot.user.role.dto.RolePageResDTO;
import com.huboot.user.role.dto.wycshop.WycShopRoleCreateReqDTO;
import com.huboot.user.role.dto.zkshop.ZkshopRoleHoumenCreateReqDTO;
import com.huboot.user.role.dto.zkshop.ZkshopRoleModifyReqDTO;
import com.huboot.user.role.dto.zkshop.ZkshopRolePageResDTO;
import com.huboot.user.user.entity.UserRoleRelationEntity;
import com.huboot.user.user.repository.IUserRepository;
import com.huboot.user.user.repository.IUserRoleRelationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户服务-角色表ServiceImpl
 */
@Service("roleServiceImpl")
public class RoleServiceImpl implements IRoleService {

    private Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private UserCacheData userCacheData;
    @Autowired
    private IUserRoleRelationRepository userRoleRelationRepository;
    @Autowired
    private IPermissionRepository permissionRepository;
    @Autowired
    private IUserRepository userRepository;

    @Transactional
    @Override
    public void create(@Valid WycShopRoleCreateReqDTO createReqDTO) throws BizException {
        Long organizationId = RequestInfo.getJwtUser().getOrganizationId();
        if (createReqDTO.getName().equals(BizConstant.WYCSHOP_MENU_ROLE_NAME)) {
            throw new BizException("超级管理员已经存在");
        }
        RoleEntity entity = new RoleEntity();
        entity.setName(createReqDTO.getName());
        entity.setOrganizationId(organizationId);
        entity.setDescription(createReqDTO.getDescription());
        entity.setSystem(Arrays.asList(OrganizationSystemEnum.wyc_shop_admin));
        roleRepository.create(entity);

        //创建菜单资源
        permissionService.createForMenu(entity.getId(), createReqDTO.getPermission());
        //创建API资源
        permissionService.createForApi(entity.getId());
    }

    @Transactional
    @Override
    public void modify(RoleModifyReqDTO modifyReqDTO) throws BizException {
        if (modifyReqDTO.getName().equals(BizConstant.WYCSHOP_MENU_ROLE_NAME)) {
            throw new BizException("超级管理员已经存在");
        }
        RoleEntity entity = roleRepository.find(modifyReqDTO.getId());
        entity.setName(modifyReqDTO.getName());
        if (!StringUtils.isEmpty(modifyReqDTO.getDescription())) entity.setDescription(modifyReqDTO.getDescription());
        roleRepository.modify(entity);
        //更新资源
        permissionService.modifyResourcesUrlByRoleIdForMenu(entity.getId(), modifyReqDTO.getPermission());

    }

    @Override
    public void modifyForZkshop(ZkshopRoleModifyReqDTO modifyReqDTO) throws BizException {
        RoleEntity entity = roleRepository.find(modifyReqDTO.getId());
        if (entity == null || !entity.getOrganizationId().equals(userCacheData.getCurrentUserEmployeeOrganizationId())) {
            throw new BizException("非法访问");
        }
        if (modifyReqDTO.getPermission() == null) {
            modifyReqDTO.setPermission(new ArrayList<>());
        }
        //更新资源
        permissionService.modifyResourcesUrlByRoleIdForMenu(entity.getId(), modifyReqDTO.getPermission());
    }

    @Override
    public void incrementRoleForZkshop(ZkshopRoleHoumenCreateReqDTO modifyReqDTO) throws BizException {
        //主要是认证的企业,店长角色一定存在,so..
        List<Long> organizationIds = new ArrayList<>();
        if (modifyReqDTO.getOrganizationId() == null) {
            Page<RoleEntity> page = roleRepository.findPage(QueryCondition.from(RoleEntity.class).where(list -> {
                list.add(ConditionMap.like("system", OrganizationSystemEnum.zk_shop.toString()));
                list.add(ConditionMap.ne("organizationId", UserConstant.ORGANIZATION_ID_PLATFORM));
            }).limit(1, Integer.MAX_VALUE));
            organizationIds = page.getContent().stream().map(RoleEntity::getOrganizationId).distinct().collect(Collectors.toList());
        } else {
            organizationIds.add(modifyReqDTO.getOrganizationId());
        }
        if (!CollectionUtils.isEmpty(organizationIds)) {
            organizationIds.forEach(organizationId -> {
                initRoleForZkShopFormDefaultRole(organizationId);
            });
        }
    }

    @Transactional
    @Override
    public void delete(Long id) throws BizException {
        //删除角色
        List<UserRoleRelationEntity> userRoleRelationEntityList = userRoleRelationRepository.findByRoleId(id);
        if (!CollectionUtils.isEmpty(userRoleRelationEntityList)) {
            throw new BizException("该角色存在关联的人员，不能删除");
        }
        userRoleRelationRepository.remove(userRoleRelationEntityList);
        roleRepository.remove(id);
    }

    @Override
    public RoleDetailResDTO find(Long id) throws BizException {
        RoleEntity entity = roleRepository.find(id);
        RoleDetailResDTO dto = new RoleDetailResDTO();
        BeanUtils.copyProperties(entity, dto);
        //查询资源
        PermissionEntity permissionEntity = permissionService.findMenuByRoleId(entity.getId());
        if (permissionEntity != null) {
            dto.setPermission(permissionEntity.getResourcesUrl());
        }
        return dto;
    }


    @Override
    public RoleDetailResDTO findMenuForZkshopByUserId(Long userId) throws BizException {
        UserEntity userEntity = userRepository.find(userId);
        final RoleDetailResDTO[] roleDetailResDTO = new RoleDetailResDTO[1];
          userEntity
                .getRoleEntities()
                .stream()
                .filter(roleEntity -> roleEntity != null && !CollectionUtils.isEmpty(roleEntity.getPermissionEntities()))
                .filter(roleEntity -> {
                    Optional<PermissionEntity> permissionEntityOptional = roleEntity.getPermissionEntities().stream()
                            .filter(permissionEntity -> permissionEntity != null && permissionEntity.getType().equals(PermissionTypeEnum.Menu))
                            .findFirst();
                    if (permissionEntityOptional.isPresent()) {
                        roleDetailResDTO[0] = new RoleDetailResDTO();
                        roleDetailResDTO[0].setPermission(permissionEntityOptional.get().getResourcesUrl());
                        return true;
                    }
                    return false;
                }).findFirst().map(roleEntity -> {
            roleDetailResDTO[0].setId(roleEntity.getId());
            roleDetailResDTO[0].setName(roleEntity.getName());
            roleDetailResDTO[0].setDescription(roleEntity.getDescription());
            roleDetailResDTO[0].setColour(roleEntity.getColour());
            return roleDetailResDTO[0];
        });
        return roleDetailResDTO[0];
    }

    @Override
    public List<RoleDetailResDTO> findAll() throws BizException {
        Long organizationId = RequestInfo.getJwtUser().getOrganizationId();
        List<RoleEntity> roleEntities = roleRepository.findByCondition(QueryCondition.from(RoleEntity.class).where(list -> {
            list.add(ConditionMap.eq("organizationId", organizationId));
            //暂时不查出超级管理员
//            list.add(ConditionMap.or(ConditionMap.eq("organizationId", organizationId), ConditionMap.eq("id", BizConstant.WYCSHOP_MENU_ROLE_ID)));
        }).sort(Sort.by("id").descending()).limit(1, Integer.MAX_VALUE));
        return roleEntities.stream().map(roleEntity -> {
            RoleDetailResDTO roleDetailResDTO = new RoleDetailResDTO();
            roleDetailResDTO.setId(roleEntity.getId());
            roleDetailResDTO.setName(roleEntity.getName());
            roleDetailResDTO.setDescription(roleEntity.getName());
            //查询资源
            PermissionEntity permissionEntity = permissionService.findMenuByRoleId(roleEntity.getId());
            roleDetailResDTO.setPermission(permissionEntity.getResourcesUrl());
            return roleDetailResDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public ShowPageImpl<RolePageResDTO> findPage(RoleQueryReqDTO queryReqDTO) throws BizException {

        Page<RoleEntity> page = roleRepository.findPage(QueryCondition.from(RoleEntity.class).where(list -> {

        }).sort(Sort.by("id").descending()).limit(queryReqDTO.getPage(), queryReqDTO.getSize()));

        return new ShowPageImpl<>(page).map(entity -> {
            RolePageResDTO dto = new RolePageResDTO();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        });
    }

    @Override
    public ShowPageImpl<WycShopRolePageResDTO> findPageForWycShop(WycShopRoleQueryReqDTO queryReqDTO) throws BizException {
        Long organizationId = RequestInfo.getJwtUser().getOrganizationId();
        Page<RoleEntity> page = roleRepository.findPage(QueryCondition.from(RoleEntity.class).where(list -> {
            if (!StringUtils.isEmpty(queryReqDTO.getName())) list.add(ConditionMap.like("name", queryReqDTO.getName()));
            list.add(ConditionMap.eq("organizationId", organizationId));
            //暂时不查出超级管理员
//            list.add(ConditionMap.or(ConditionMap.eq("organizationId", organizationId), ConditionMap.eq("id", BizConstant.WYCSHOP_MENU_ROLE_ID)));
        }).sort(Sort.by("id").descending()).limit(queryReqDTO.getPage(), queryReqDTO.getSize()));

        return new ShowPageImpl<>(page).map(entity -> {
            WycShopRolePageResDTO dto = new WycShopRolePageResDTO();
            BeanUtils.copyProperties(entity, dto);
            dto.setModifyName(userCacheData.getUserDetailInfo(entity.getModifierId()).getUser().getName());
            return dto;
        });
    }

    @Override
    public ShowPageImpl<ZkshopRolePageResDTO> findPageForZkShop(ZkshopRoleQueryReqDTO queryReqDTO) throws BizException {
        Page<RoleEntity> page = roleRepository.findPage(QueryCondition.from(RoleEntity.class).where(list -> {
            list.add(ConditionMap.eq("organizationId", userCacheData.getCurrentUserEmployeeOrganizationId()));
            list.add(ConditionMap.ne("name", BizConstant.ZKSHOP_MANAGER_ROLE_NAME));
        }).sort(Sort.by("id").descending()).limit(queryReqDTO.getPage(), queryReqDTO.getSize()));
        return new ShowPageImpl<>(page).map(entity -> {
            ZkshopRolePageResDTO dto = new ZkshopRolePageResDTO();
            BeanUtils.copyProperties(entity, dto);
            PermissionEntity permissionEntity = permissionService.findMenuByRoleId(entity.getId());
            dto.setPermission(permissionEntity.getResourcesUrl());
            return dto;
        });
    }

    @Override
    public void createRoleForOrganizationCompany(Long userId) {
        //用户角色关系:
        UserRoleRelationEntity userRoleRelationEntity = new UserRoleRelationEntity();
        userRoleRelationEntity.setRoleId(BizConstant.WYCSHOP_ROLE_ID);
        userRoleRelationEntity.setUserId(userId);
        userRoleRelationRepository.create(userRoleRelationEntity);
    }

    @Override
    public void createRoleForAdmin(Long userId) {
        //用户角色关系:
        UserRoleRelationEntity userRoleRelationEntity = new UserRoleRelationEntity();
        userRoleRelationEntity.setRoleId(BizConstant.ADMIN_API_ROLE_ID);
        userRoleRelationEntity.setUserId(userId);
        userRoleRelationRepository.create(userRoleRelationEntity);
    }

    @Override
    public void createRoleForWycShop(Long userId, Long roleId) {
        RoleEntity roleEntity = roleRepository.find(roleId);
        AppAssert.notNull(roleEntity, "角色不存在");
        //用户角色关系:
        UserRoleRelationEntity userRoleRelationEntity = new UserRoleRelationEntity();
        userRoleRelationEntity.setRoleId(roleId);
        userRoleRelationEntity.setUserId(userId);
        userRoleRelationRepository.create(userRoleRelationEntity);
    }

    @Override
    public void createRoleForWycMiniapp(Long userId) {
        UserRoleRelationEntity userRoleRelationEntity = userRoleRelationRepository.findByUserIdAndRoleId(userId, BizConstant.WYCDRIVERMINIAPP_ROLE_ID);
        if (userRoleRelationEntity == null) {
            userRoleRelationEntity = new UserRoleRelationEntity();
            userRoleRelationEntity.setUserId(userId);
            userRoleRelationEntity.setRoleId(BizConstant.WYCDRIVERMINIAPP_ROLE_ID);
            userRoleRelationRepository.create(userRoleRelationEntity);
        }
    }

    @Override
    @Transactional
    public void createRoleForZkShopFormDefaultRole(Long userId, Long organizationId, Long roleId) {
        Long newRoleId = createRoleForZkShopFormRole(organizationId, roleId);
        if (newRoleId != null) {
            //创建关系
            UserRoleRelationEntity userRoleRelationEntity = new UserRoleRelationEntity();
            userRoleRelationEntity.setUserId(userId);
            userRoleRelationEntity.setRoleId(newRoleId);
            userRoleRelationRepository.create(userRoleRelationEntity);
        }
    }


    private Long createRoleForZkShopFormRole(Long organizationId, Long roleId) {
        RoleEntity roleEntity = roleRepository.find(roleId);
        //check组织下是否存在相同的权限,通过名称匹配
        List<RoleEntity> roleEntityList = roleRepository.findByOrganizationId(organizationId);

        if (!CollectionUtils.isEmpty(roleEntityList)) {
            List<RoleEntity> roleEntities = roleEntityList.stream().filter(roleEntity1 -> roleEntity1.getName().equals(roleEntity.getName())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(roleEntities)) {
                return roleEntities.get(0).getId();
            }
        }
        RoleEntity entity = new RoleEntity();
        entity.setName(roleEntity.getName());
        entity.setOrganizationId(organizationId);
        entity.setDescription(roleEntity.getDescription());
        entity.setSystem(roleEntity.getSystem());
        entity.setColour(roleEntity.getColour());
        roleRepository.create(entity);

        //创建菜单资源
        PermissionEntity permissionEntity = permissionService.findMenuByRoleId(roleId);
        PermissionEntity newPermissionEntity = new PermissionEntity();
        newPermissionEntity.setResourcesMethod(permissionEntity.getResourcesMethod());
        newPermissionEntity.setResourcesUrl(permissionEntity.getResourcesUrl());
        newPermissionEntity.setDescription(permissionEntity.getDescription());
        newPermissionEntity.setRoleId(entity.getId());
        newPermissionEntity.setType(permissionEntity.getType());
        permissionRepository.create(newPermissionEntity);

        //创建API资源
        PermissionEntity permissionEntityApi = permissionService.findApiByRoleId(roleId);
        PermissionEntity newPermissionEntityApi = new PermissionEntity();
        newPermissionEntityApi.setResourcesMethod(permissionEntityApi.getResourcesMethod());
        newPermissionEntityApi.setResourcesUrl(permissionEntityApi.getResourcesUrl());
        newPermissionEntityApi.setDescription(permissionEntityApi.getDescription());
        newPermissionEntityApi.setRoleId(entity.getId());
        newPermissionEntityApi.setType(permissionEntityApi.getType());
        permissionRepository.create(newPermissionEntityApi);
        return entity.getId();
    }

    @Override
    @Transactional
    public void initRoleForZkShopFormDefaultRole(Long organizationId) {
        createRoleForZkShopFormRole(organizationId, BizConstant.ZKSHOP_MANAGER_ROLE_ID);
        createRoleForZkShopFormRole(organizationId, BizConstant.ZKSHOP_FINANCE_ROLE_ID);
        createRoleForZkShopFormRole(organizationId, BizConstant.ZKSHOP_DRIVER_ROLE_ID);
    }

    @Override
    public void createInitRoleForZkShop(Long userId) {
        UserRoleRelationEntity userRoleRelationEntity = userRoleRelationRepository.findByUserIdAndRoleId(userId, BizConstant.ZKSHOP_INIT_ROLE_ID);
        if (userRoleRelationEntity == null) {
            userRoleRelationEntity = new UserRoleRelationEntity();
            userRoleRelationEntity.setUserId(userId);
            userRoleRelationEntity.setRoleId(BizConstant.ZKSHOP_INIT_ROLE_ID);
            userRoleRelationRepository.create(userRoleRelationEntity);
        }
    }

    @Override
    public void createInitRoleForZkUser(Long userId) {
        UserRoleRelationEntity userRoleRelationEntity = userRoleRelationRepository.findByUserIdAndRoleId(userId, BizConstant.ZKUSER_ROLE_ID);
        if (userRoleRelationEntity == null) {
            userRoleRelationEntity = new UserRoleRelationEntity();
            userRoleRelationEntity.setUserId(userId);
            userRoleRelationEntity.setRoleId(BizConstant.ZKUSER_ROLE_ID);
            userRoleRelationRepository.create(userRoleRelationEntity);
        }
    }
    @Override
    public void modifyUserRoleById(UserEntity entity, Long roleId) {
        RoleEntity roleEntityResult = roleRepository.find(roleId);
        AppAssert.notNull(roleEntityResult, "角色不存在");
        List<RoleEntity> roleEntities = entity.getRoleEntities();
        RoleEntity roleEntity = roleEntities.stream().filter(r -> r.getSystem().contains(OrganizationSystemEnum.wyc_shop_admin)).findFirst().get();
        UserRoleRelationEntity userRoleRelationEntity = userRoleRelationRepository.findByUserIdAndRoleId(entity.getId(), roleEntity.getId());
        userRoleRelationEntity.setRoleId(roleId);
        userRoleRelationRepository.modify(userRoleRelationEntity);
    }
}
