package com.huboot.user.user.service.impl;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.jpa.ConditionMap;
import com.huboot.commons.jpa.QueryCondition;
import com.huboot.commons.page.ShowPageImpl;
import com.huboot.commons.sso.PermissionResourcesDTO;
import com.huboot.commons.sso.SSOConstant;
import com.huboot.commons.sso.SSORedisHashName;
import com.huboot.commons.utils.AppAssert;
import com.huboot.commons.utils.CommonTools;
import com.huboot.commons.utils.SnGenerator;
import com.huboot.share.user_service.api.dto.UserDetailInfo;
import com.huboot.share.user_service.data.UserCacheData;
import com.huboot.share.user_service.enums.*;
import com.huboot.user.organization.entity.OrganizationEntity;
import com.huboot.user.organization.repository.IOrganizationCompanyRepository;
import com.huboot.user.organization.repository.IOrganizationRepository;
import com.huboot.user.permission.repository.IPermissionRepository;
import com.huboot.user.role.entity.RoleEntity;
import com.huboot.user.role.repository.IRoleRepository;
import com.huboot.user.role.service.IRoleService;
import com.huboot.user.user.dto.UserDetailDTO;
import com.huboot.user.user.dto.UserModifyReqDTO;
import com.huboot.user.user.dto.UserQueryReqDTO;
import com.huboot.user.user.dto.admin.*;
import com.huboot.user.user.dto.wycshop.*;
import com.huboot.user.user.dto.zkshop.UserCreateZkShopReqDTO;
import com.huboot.user.user.dto.zkuser.UserCreateZkUserReqDTO;
import com.huboot.user.user.entity.UserEntity;
import com.huboot.user.user.entity.UserPersonalEntity;
import com.huboot.user.user.repository.IUserEmployeeRepository;
import com.huboot.user.user.repository.IUserPersonalRepository;
import com.huboot.user.common.constant.UserConstant;
import com.huboot.user.organization.entity.OrganizationCompanyEntity;
import com.huboot.user.organization.entity.OrganizationShopEntity;
import com.huboot.user.organization.service.IOrganizationCompanyService;
import com.huboot.user.permission.entity.PermissionEntity;
import com.huboot.user.permission.service.IPermissionService;
import com.huboot.user.user.dto.UserPageResDTO;
import com.huboot.user.user.dto.wycdriverminiapp.UserCreateMiniAppReqDTO;
import com.huboot.user.user.entity.UserEmployeeEntity;
import com.huboot.user.user.entity.UserRoleRelationEntity;
import com.huboot.user.user.repository.IUserRepository;
import com.huboot.user.user.repository.IUserRoleRelationRepository;
import com.huboot.user.user.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户服务-用户基础信息表ServiceImpl
 */
@Service("userServiceImpl")
public class UserServiceImpl implements IUserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IOrganizationRepository organizationRepository;
    @Autowired
    private IOrganizationCompanyRepository organizationCompanyRepository;
    @Autowired
    private IOrganizationCompanyService organizationCompanyService;
    /*@Autowired
    private SubAccountFeignClient subAccountFeignClient;*/
    @Autowired
    private IUserRoleRelationRepository userRoleRelationRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IUserPersonalRepository userPersonalRepository;
    @Autowired
    private UserCacheData userCacheData;
    @Autowired
    private IUserEmployeeRepository userEmployeeRepository;
    @Autowired
    private IPermissionRepository permissionRepository;

    /**
     * 根据查询条件获取userid List
     * 每个条件进行模糊匹配
     *
     * @param name
     * @param phone
     * @param idcard
     * @return
     */
    @Override
    public List<Long> getUserIdListCondition(String name, String phone, String idcard) {
        if (StringUtils.isEmpty(name) && StringUtils.isEmpty(phone) && StringUtils.isEmpty(idcard)) {
            return new ArrayList<>();
        }
        QueryCondition queryCondition = QueryCondition.from(UserEntity.class).innerJoin("organizationEntity").innerJoin("userPersonalEntity").where(list -> {
            list.add(ConditionMap.like("organizationEntity.system", OrganizationSystemEnum.wyc_shop_admin));
            if (!StringUtils.isEmpty(idcard))
                list.add(ConditionMap.like("userPersonalEntity.num", idcard));
            if (!StringUtils.isEmpty(phone))
                list.add(ConditionMap.like("phone", phone));
            if (!StringUtils.isEmpty(name)) list.add(ConditionMap.like("name", name));
        }).sort(Sort.by("id").descending()).limit(Integer.MAX_VALUE);
        List<UserEntity> userEntityList = userRepository.findByCondition(queryCondition);
        if (CollectionUtils.isEmpty(userEntityList)) {
            return new ArrayList<>();
        }
        List<Long> list = userEntityList.stream().map(UserEntity::getId).collect(Collectors.toList());
        return list;
    }


    @Transactional
    @Override
    public void createAdmin(@Valid UserCreateAdminReqDTO createReqDTO) throws BizException {
        //如果手机号已经存在，就关联
        UserEntity entity = userRepository.findByPhoneAndOrganizationId(createReqDTO.getPhone(), UserConstant.ORGANIZATION_ID_XIEHUA);
        if (entity != null) {
            throw new BizException("该手机号已经存在");
        }
        //根组织
        OrganizationEntity organizationEntity = organizationRepository.find(UserConstant.ORGANIZATION_ID_XIEHUA);
        AppAssert.notNull(organizationEntity, "根组织不存在");
        if (entity == null) {
            entity = new UserEntity();
            entity.setUsername(organizationCompanyService.generatorUsername(organizationEntity.getId()));
            entity.setRegisterSystem(OrganizationSystemEnum.xiehua_admin);
            entity.setOrganizationId(organizationEntity.getId());
            entity.setUserType(UserTypeEnum.person);
            entity.setPassword(passwordEncoder.encode(createReqDTO.getPassword()));
            entity.setName(createReqDTO.getName());
            entity.setPhone(createReqDTO.getPhone());
            this.createUser(entity, organizationEntity);
        } else {
            userRepository.modify(entity);
        }
        //员工信息
        UserEmployeeEntity userEmployeeEntity = new UserEmployeeEntity();
        userEmployeeEntity.setStatus(UserEmployeeStatusEnum.enabled);
        userEmployeeEntity.setUserId(entity.getId());
        userEmployeeEntity.setOrganizationId(organizationEntity.getId());
        userEmployeeEntity.setJobNumber("");
        userEmployeeRepository.create(userEmployeeEntity);
        //用户角色关系
        roleService.createRoleForAdmin(entity.getId());
    }

    @Override
    @Transactional
    public void createOrganizationCompany(@Valid UserCreateCompanyReqDTO createReqDTO) throws BizException {

        //根组织
        OrganizationEntity organizationEntity = organizationRepository.find(createReqDTO.getOrganizationId());
        AppAssert.notNull(organizationEntity, "根组织不存在");
        if (!organizationEntity.getParentId().equals(UserConstant.ORGANIZATION_ROOT_ID)) {
            throw new BizException("根组织不存在");
        }
        //判断是否存在用户
        List<UserEmployeeEntity> userEmployeeEntities = userEmployeeRepository.findByCondition(
                QueryCondition.from(UserEmployeeEntity.class).innerJoin("userEntity").where(list -> {
                    list.add(ConditionMap.eq("organizationId", organizationEntity.getId()));
                }));
        if (!CollectionUtils.isEmpty(userEmployeeEntities)) {
            throw new BizException("一个公司只能创建一个账号");
        }
        UserEntity entity = new UserEntity();
        //公司信息
        OrganizationCompanyEntity organizationCompanyEntity = organizationCompanyRepository.findByOrganizationId(organizationEntity.getId());
        entity.setUsername(organizationCompanyService.generatorUsername(organizationEntity.getId()));
        //1、一开始定义的是随机密码，后来需求改成了：公司代码+123456
//        String password = CommonTools.getRandom(6);
        String password = organizationCompanyEntity.getCode() + "123456";
        entity.setPassword(passwordEncoder.encode(password));
        entity.setPhone(createReqDTO.getPhone());
        entity.setName(createReqDTO.getName());
        entity.setUserType(UserTypeEnum.company);
        entity.setOrganizationId(organizationEntity.getId());
        entity.setRegisterSystem(OrganizationSystemEnum.xiehua_admin);
        this.createUser(entity, organizationEntity);
        //员工信息
        UserEmployeeEntity userEmployeeEntity = new UserEmployeeEntity();
        userEmployeeEntity.setStatus(UserEmployeeStatusEnum.enabled);
        userEmployeeEntity.setUserId(entity.getId());
        userEmployeeEntity.setOrganizationId(organizationEntity.getId());
        userEmployeeEntity.setJobNumber("");
        userEmployeeRepository.create(userEmployeeEntity);
        //创建管理员角色
        roleService.createRoleForOrganizationCompany(entity.getId());

    }

    @Override
    @Transactional
    public void createWycShop(@Valid WycShopCreateCompanyReqDTO createReqDTO) throws BizException {
        Long organizationId = userCacheData.getCurrentUserOrgId();
        //查询员工信息
        List<UserEmployeeEntity> userEmployeeEntities = userEmployeeRepository.findByCondition(QueryCondition.from(UserEmployeeEntity.class).innerJoin("userEntity").where(list -> {
            list.add(ConditionMap.eq("userEntity.phone", createReqDTO.getPhone()));
            list.add(ConditionMap.eq("organizationId", organizationId));
        }));
        if (!CollectionUtils.isEmpty(userEmployeeEntities)) {
            throw new BizException("该手机号已经存在");
        }
        //判断是否存在用户
        UserEntity entity = userRepository.findByPhoneAndOrganizationId(createReqDTO.getPhone(), organizationId);
        if (entity == null) {
            entity = new UserEntity();
            entity.setName(createReqDTO.getName());
            //公司信息
            entity.setUsername(organizationCompanyService.generatorUsername(organizationId));
            entity.setUserType(UserTypeEnum.person);
            String password = organizationCompanyRepository.findByOrganizationId(organizationId).getCode() + "123456";
            entity.setPassword(passwordEncoder.encode(password));
            entity.setUserStatus(UserStatusEnum.default_status);
            entity.setOrganizationId(organizationId);
            entity.setRegisterSystem(OrganizationSystemEnum.wyc_shop_admin);
            entity.setThirdOpenId(new ArrayList<>());
            entity.setPhone(createReqDTO.getPhone());
            OrganizationEntity organizationEntity = organizationRepository.find(organizationId);
            this.createUser(entity, organizationEntity);
        } else {
            entity.setPhone(createReqDTO.getPhone());
            entity.setName(createReqDTO.getName());
            //公司信息
            entity.setUsername(organizationCompanyService.generatorUsername(organizationId));
            //填坑-已有的用户，需要对密码重置
            String password = organizationCompanyRepository.findByOrganizationId(organizationId).getCode() + "123456";
            entity.setPassword(passwordEncoder.encode(password));
            userRepository.modify(entity);
        }

        // 新增员工信息
        UserEmployeeEntity userEmployeeEntity = new UserEmployeeEntity();
        userEmployeeEntity.setJobNumber(createReqDTO.getJobNumber());
        userEmployeeEntity.setUserId(entity.getId());
        userEmployeeEntity.setStatus(UserEmployeeStatusEnum.enabled);
        userEmployeeEntity.setOrganizationId(organizationId);
        userEmployeeRepository.create(userEmployeeEntity);
        // 新增角色信息
        roleService.createRoleForWycShop(entity.getId(), createReqDTO.getRoleId());
    }

    @Override
    public void modifyWycShop(WycShopModifyCompanyReqDTO createReqDTO) throws BizException {
        //判断是否存在用户
        Long organizationId = userCacheData.getCurrentUserOrgId();
        UserEntity entity = userRepository.findByPhoneAndOrganizationId(createReqDTO.getPhone(), organizationId);
        if (entity != null && !entity.getId().equals(createReqDTO.getId())) {
            throw new BizException("手机号已存在");
        }
        entity = userRepository.find(createReqDTO.getId());
        entity.setName(createReqDTO.getName());
        entity.setPhone(createReqDTO.getPhone());
        userRepository.modify(entity);
        //员工信息
        UserEmployeeEntity userEmployeeEntity = userEmployeeRepository.findByUserId(entity.getId());
        userEmployeeEntity.setJobNumber(createReqDTO.getJobNumber());
        userEmployeeRepository.modify(userEmployeeEntity);

        // 修改角色信息
        roleService.modifyUserRoleById(entity, createReqDTO.getRoleId());

    }

    @Override
    public void modifyWycShopStatus(Long userId, UserEmployeeStatusEnum status) throws BizException {
        UserEntity userEntity = userRepository.find(userId);
        //员工信息
        UserEmployeeEntity userEmployeeEntity = userEmployeeRepository.findByUserId(userEntity.getId());
        userEmployeeEntity.setStatus(status);
        userEmployeeRepository.modify(userEmployeeEntity);
    }

    @Override
    public void createPermission(PermissionCreateReqDTO wycShopRoleCreateReqDTO) {
        //创建API资源
        permissionService.createForAllApi(wycShopRoleCreateReqDTO);
    }

    @Override
    public void userInfoToRedis() {
        Map<String, List<String>> user_permission = new HashMap<>();
        Map<String, List<PermissionResourcesDTO>> url_permission = new HashMap<>();
        //用户角色
        List<UserRoleRelationEntity> userRoleRelationEntityList = userRoleRelationRepository.findAll();
        userRoleRelationEntityList.stream().forEach(userRoleRelationEntity -> {
            List<String> roles = user_permission.get(String.valueOf(userRoleRelationEntity.getUserId()));
            if (CollectionUtils.isEmpty(roles)) {
                roles = new ArrayList<>();
            }
            roles.add(String.valueOf(userRoleRelationEntity.getRoleId()));
            user_permission.put(String.valueOf(userRoleRelationEntity.getUserId()), roles);
        });
        redisTemplate.opsForHash().putAll(SSORedisHashName.USER_PERMISSION, user_permission);

        //角色资源
        url_permission = permissionService.findByType(PermissionTypeEnum.API);

        redisTemplate.opsForHash().putAll(SSORedisHashName.URL_PERMISSION, url_permission);
    }

    @Override
    @Transactional
    public UserEntity createOrModifyUserForWycMiniapp(UserCreateMiniAppReqDTO createReqDTO) throws BizException {
        //如果手机号已经存在，就关联
        UserEntity entity = userRepository.findByPhoneAndOrganizationId(createReqDTO.getPhone(), createReqDTO.getOrganizationId());
        UserEntity.ThirdOpenId thirdOpenId =
                new UserEntity.ThirdOpenId(ThirdPlatformEnum.miniapp, createReqDTO.getAppId(), createReqDTO.getOpenId(), OrganizationSystemEnum.wyc_driver_miniapp);
        if (entity == null) {
            entity = new UserEntity();
            entity.setUsername(SnGenerator.generatorUsername());
            entity.setPhone(createReqDTO.getPhone());
            String password = CommonTools.getRandom(6);
            entity.setPassword(passwordEncoder.encode(password));
            entity.setUserType(UserTypeEnum.person);
            entity.setOrganizationId(createReqDTO.getOrganizationId());
            entity.setThirdOpenId(Arrays.asList(thirdOpenId));
            entity.setRegisterSystem(OrganizationSystemEnum.wyc_driver_miniapp);
            entity.setUserStatus(UserStatusEnum.default_status);
            OrganizationEntity organizationEntity = organizationRepository.find(createReqDTO.getOrganizationId());
            this.createUser(entity, organizationEntity);
        } else {
            this.modifyUserForMiniapp(entity, thirdOpenId);
        }
        roleService.createRoleForWycMiniapp(entity.getId());
        return entity;
    }

    @Override
    @Transactional
    public UserEntity createOrModifyUserForZkShop(UserCreateZkShopReqDTO createReqDTO) throws BizException {
        //如果手机号已经存在，就关联
        UserEntity entity = userRepository.findByPhoneAndOrganizationId(createReqDTO.getPhone(), createReqDTO.getOrganizationId());
        UserEntity.ThirdOpenId thirdOpenId = new UserEntity.ThirdOpenId(ThirdPlatformEnum.miniapp,createReqDTO.getAppId(), createReqDTO.getOpenId(), OrganizationSystemEnum.zk_shop);
        if (entity == null) {
            entity = new UserEntity();
            entity.setUsername(SnGenerator.generatorUsername());
            entity.setPhone(createReqDTO.getPhone());
            String password = CommonTools.getRandom(6);
            entity.setPassword(passwordEncoder.encode(password));
            entity.setUserType(UserTypeEnum.person);
            entity.setOrganizationId(createReqDTO.getOrganizationId());
            entity.setThirdOpenId(Arrays.asList(thirdOpenId));
            entity.setRegisterSystem(OrganizationSystemEnum.zk_shop);
            entity.setUserStatus(UserStatusEnum.default_status);
            OrganizationEntity organizationEntity = organizationRepository.find(createReqDTO.getOrganizationId());
            this.createUser(entity, organizationEntity);
        } else {
            this.modifyUserForMiniapp(entity, thirdOpenId);
        }
        roleService.createInitRoleForZkShop(entity.getId());
        return entity;
    }

    @Override
    @Transactional
    public UserEntity createOrModifyUserForZkUser(UserCreateZkUserReqDTO createReqDTO) throws BizException {
        //如果手机号已经存在，就关联
        UserEntity entity = userRepository.findByPhoneAndOrganizationId(createReqDTO.getPhone(), createReqDTO.getOrganizationId());
        UserEntity.ThirdOpenId thirdOpenId = new UserEntity.ThirdOpenId(ThirdPlatformEnum.miniapp,createReqDTO.getAppId(), createReqDTO.getOpenId(), OrganizationSystemEnum.zk_user);
        if (entity == null) {
            entity = new UserEntity();
            entity.setUsername(SnGenerator.generatorUsername());
            entity.setPhone(createReqDTO.getPhone());
            String password = CommonTools.getRandom(6);
            entity.setPassword(passwordEncoder.encode(password));
            entity.setUserType(UserTypeEnum.person);
            entity.setOrganizationId(createReqDTO.getOrganizationId());
            entity.setThirdOpenId(Arrays.asList(thirdOpenId));
            entity.setRegisterSystem(OrganizationSystemEnum.zk_user);
            entity.setUserStatus(UserStatusEnum.default_status);
            OrganizationEntity organizationEntity = organizationRepository.find(createReqDTO.getOrganizationId());
            this.createUser(entity, organizationEntity);
        } else {
            this.modifyUserForMiniapp(entity, thirdOpenId);
        }
        roleService.createInitRoleForZkUser(entity.getId());
        return entity;
    }

    private UserEntity modifyUserForMiniapp(UserEntity entity, UserEntity.ThirdOpenId thirdOpenId) throws BizException {
        List<UserEntity.ThirdOpenId> thirdOpenIds = entity.getThirdOpenId();
        if (!CollectionUtils.isEmpty(thirdOpenIds)) {
            Long count = thirdOpenIds.stream().filter(oldThirdOpenId -> oldThirdOpenId.getThirdName().equals(thirdOpenId.getThirdName())).count();
            if (count > 0) {
                //替换
                thirdOpenIds.forEach(oldThirdOpenId -> {
                    if (oldThirdOpenId.getThirdName().equals(thirdOpenId.getThirdName())) {
                        oldThirdOpenId.setValue(thirdOpenId.getValue());
                        oldThirdOpenId.setSystem(thirdOpenId.getSystem());
                    }
                });
            } else {
                thirdOpenIds.add(thirdOpenId);
            }
        } else {
            thirdOpenIds.add(thirdOpenId);
        }
        entity.setThirdOpenId(thirdOpenIds);
        userRepository.modify(entity);
        return entity;
    }

    @Transactional
    @Override
    public void modify(UserModifyReqDTO modifyReqDTO) throws BizException {
        UserEntity entity = userRepository.find(modifyReqDTO.getId());
        BeanUtils.copyProperties(modifyReqDTO, entity);
        userRepository.modify(entity);
    }

    @Override
    public void modifyOrganizationCompany(UserModifyCompanyReqDTO modifyReqDTO) throws BizException {
        UserEntity userEntity = userRepository.find(modifyReqDTO.getId());
        userEntity.setName(modifyReqDTO.getName());
        userEntity.setPhone(modifyReqDTO.getPhone());
        userRepository.modify(userEntity);
    }

    @Override
    public void modifyAdmin(UserModifyAdminReqDTO modifyReqDTO) throws BizException {
        UserEntity userEntity = userRepository.findByPhoneAndOrganizationId(modifyReqDTO.getPhone(), UserConstant.ORGANIZATION_ID_XIEHUA);
        if (userEntity != null && !userEntity.getId().equals(modifyReqDTO.getId())) {
            throw new BizException("该手机号已经存在");
        }
        userEntity.setName(modifyReqDTO.getName());
        userEntity.setPhone(modifyReqDTO.getPhone());
        userRepository.modify(userEntity);
    }

    @Transactional
    @Override
    public void delete(Long id) throws BizException {
        userRepository.remove(id);
    }

    @Override
    public UserDetailDTO findDetail() throws BizException {
        UserDetailDTO dto = new UserDetailDTO();
        UserDetailInfo info = userCacheData.getCurrentUser();
        UserEntity entity = userRepository.find(info.getUser().getUserId());
        dto.setId(entity.getId());
        dto.setGuid(entity.getGuid());
        dto.setUsername(entity.getUsername());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setImagePath(entity.getImagePath());
        dto.setName(entity.getName());
        dto.setNickName(entity.getNickName());
        dto.setIdcard(info.getUserPersonal().getIdCard());
        return dto;
    }

    @Override
    public WycShopUserDetailResDTO findForWycShop(Long id) throws BizException {
        UserEntity entity = userRepository.find(id);
        WycShopUserDetailResDTO wycShopUserDetailResDTO = new WycShopUserDetailResDTO();
        wycShopUserDetailResDTO.setId(entity.getId());
        wycShopUserDetailResDTO.setName(entity.getName());
        wycShopUserDetailResDTO.setPhone(entity.getPhone());
        List<RoleEntity> roleEntities = entity.getRoleEntities();
        RoleEntity roleEntity = roleEntities.stream().filter(r -> r.getSystem().contains(OrganizationSystemEnum.wyc_shop_admin)).findFirst().get();
        wycShopUserDetailResDTO.setRoleName(roleEntity.getName());
        wycShopUserDetailResDTO.setUsername(entity.getUsername());
        UserEmployeeEntity userEmployeeEntity = userEmployeeRepository.findByUserId(entity.getId());
        wycShopUserDetailResDTO.setJobNumber(userEmployeeEntity.getJobNumber());
        //查询资源
        PermissionEntity permissionEntity = roleEntity.getPermissionEntities().stream().filter(p -> p.getType().equals(PermissionTypeEnum.Menu)).findFirst().get();
        wycShopUserDetailResDTO.setPermission(permissionEntity.getResourcesUrl());
        return wycShopUserDetailResDTO;
    }

    /**
     * 用户详情缓存数据
     *
     * @param userId
     * @return
     */
    @Override
    public UserDetailInfo findForInner(Long userId) throws BizException {
        UserDetailInfo userDetailInfo = new UserDetailInfo();
        //用户
        UserEntity entity = userRepository.find(userId);
        OrganizationEntity organizationEntity = entity.getOrganizationEntity();
        OrganizationCompanyEntity organizationCompanyEntity = organizationEntity.getOrganizationCompanyEntity();
        userDetailInfo.getUser().setUserId(entity.getId());
        userDetailInfo.getUser().setName(entity.getName());
        userDetailInfo.getUser().setOrganizationId(entity.getOrganizationId());
        userDetailInfo.getUser().setPhone(entity.getPhone());
        userDetailInfo.getUser().setCompanyId(organizationCompanyEntity.getId());
        userDetailInfo.getUser().setCompanySn(organizationCompanyEntity.getSn());
        List<OrganizationShopEntity> shopEntityList = organizationEntity.getOrganizationShopEntity();
        //网约车店铺信息
        //产品坑:2018版网约车--c端user属于公司的organizationId:
        //2019版网约车--c端user属于平台的organizationId;
        if (!CollectionUtils.isEmpty(shopEntityList) && !CollectionUtils.isEmpty(shopEntityList)) {
            Optional<OrganizationShopEntity> organizationShopEntityOptional = organizationEntity.getOrganizationShopEntity().stream().filter(shopEntity ->
                    shopEntity.getSystem().contains(OrganizationSystemEnum.wyc_driver_miniapp)
            ).findFirst();
            if (organizationShopEntityOptional.isPresent()) {
                userDetailInfo.getUser().setWycShopId(organizationShopEntityOptional.get().getId());
                userDetailInfo.getUser().setWycShopSn(organizationShopEntityOptional.get().getSn());
            }
        }
        List<UserEntity.ThirdOpenId> thirdOpenIds = entity.getThirdOpenId();
        if (!CollectionUtils.isEmpty(thirdOpenIds)) {
            for (UserEntity.ThirdOpenId thirdOpenId : thirdOpenIds) {
                UserDetailInfo.ThirdOpenId thirdOpenId1 = new UserDetailInfo.ThirdOpenId();
                thirdOpenId1.setSystem(thirdOpenId.getSystem());
                thirdOpenId1.setThirdName(thirdOpenId.getThirdName());
                thirdOpenId1.setValue(thirdOpenId.getValue());
                userDetailInfo.getUser().getThirdOpenId().add(thirdOpenId1);
            }
        }
        //个人信息
        UserPersonalEntity personalEntity = entity.getUserPersonalEntity();
        if (null != personalEntity) {
            userDetailInfo.getUserPersonal().setIdCard(personalEntity.getNum());
            userDetailInfo.getUserPersonal().setName(personalEntity.getName());
        }
        //员工信息
        UserEmployeeEntity userEmployeeEntity = entity.getUserEmployeeEntity();
        if (userEmployeeEntity != null) {
            userDetailInfo.getUserEmployee().setOrganizationId(userEmployeeEntity.getOrganizationId());
            userDetailInfo.getUserEmployee().setCompanyId(userEmployeeEntity.getOrganizationEntity().getOrganizationCompanyEntity().getId());
            userDetailInfo.getUserEmployee().setCompanySn(userEmployeeEntity.getOrganizationEntity().getOrganizationCompanyEntity().getSn());
            userEmployeeEntity.getOrganizationEntity().getOrganizationShopEntity().stream().forEach(shopEntity -> {
                        if (shopEntity != null && shopEntity.getSystem().contains(OrganizationSystemEnum.zk_shop) && userDetailInfo.getUserEmployee().getZkShopId() == null) {
                            userDetailInfo.getUserEmployee().setZkShopId(shopEntity.getId());
                            userDetailInfo.getUserEmployee().setZkShopSn(shopEntity.getSn());
                        }
                        if (shopEntity != null && shopEntity.getSystem().contains(OrganizationSystemEnum.wyc_shop_admin) && userDetailInfo.getUserEmployee().getWycShopId() == null) {
                            userDetailInfo.getUserEmployee().setWycShopId(shopEntity.getId());
                            userDetailInfo.getUserEmployee().setWycShopSn(shopEntity.getSn());
                        }
                    }
            );
        }
        return userDetailInfo;
    }

    @Override
    public List<String> getUserRole(Long userId) throws BizException {
        List<UserRoleRelationEntity> userRoleRelationDetailResDTOS = userRoleRelationRepository.findByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleRelationDetailResDTOS)) {
            return new ArrayList<>();
        }
        return userRoleRelationDetailResDTOS.stream().map(userRoleRelationEntity -> String.valueOf(userRoleRelationEntity.getRoleId())).collect(Collectors.toList());
    }

    @Override
    public UserDetailCompanyResDTO findCompany(Long id) throws BizException {
        UserEntity entity = userRepository.find(id);
        UserDetailCompanyResDTO dto = new UserDetailCompanyResDTO();
        BeanUtils.copyProperties(entity, dto);
        OrganizationEntity organizationEntity = organizationRepository.find(entity.getOrganizationId());
        dto.setOrganizationName(organizationEntity.getName());
        return dto;
    }

    @Override
    public UserDetailAdminResDTO findAdmin(Long id) throws BizException {
        UserEntity entity = userRepository.find(id);
        UserDetailAdminResDTO dto = new UserDetailAdminResDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public ShowPageImpl<UserPageResDTO> findPage(UserQueryReqDTO queryReqDTO) throws BizException {

        Page<UserEntity> page = userRepository.findPage(QueryCondition.from(UserEntity.class).where(list -> {

        }).sort(Sort.by("id").descending()).limit(queryReqDTO.getPage(), queryReqDTO.getSize()));

        return new ShowPageImpl<>(page).map(entity -> {
            UserPageResDTO dto = new UserPageResDTO();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        });
    }

    @Override
    public ShowPageImpl<UserWycShopPageResDTO> findPageForWycShop(UserQueryWycShopReqDTO queryReqDTO) throws BizException {
        Long organizationId = userCacheData.getCurrentUserOrgId();

        Page<UserEmployeeEntity> page = userEmployeeRepository.findPage(QueryCondition.from(UserEmployeeEntity.class).innerJoin("userEntity").innerJoin("userEntity.roleEntities").where(list -> {
            list.add(ConditionMap.eq("organizationId", organizationId));
            if (!StringUtils.isEmpty(queryReqDTO.getJobNumber()))
                list.add(ConditionMap.like("jobNumber", queryReqDTO.getJobNumber()));
            if (!StringUtils.isEmpty(queryReqDTO.getUsername()))
                list.add(ConditionMap.like("userEntity.username", queryReqDTO.getUsername()));
            if (!StringUtils.isEmpty(queryReqDTO.getPhone()))
                list.add(ConditionMap.like("userEntity.phone", queryReqDTO.getPhone()));
            if (!StringUtils.isEmpty(queryReqDTO.getName()))
                list.add(ConditionMap.like("userEntity.name", queryReqDTO.getName()));
            if (!StringUtils.isEmpty(queryReqDTO.getRoleId()))
                list.add(ConditionMap.eq("userEntity.roleEntities.id", queryReqDTO.getRoleId()));
            if (!StringUtils.isEmpty(queryReqDTO.getUserEmployeeStatus()))
                list.add(ConditionMap.eq("status", queryReqDTO.getUserEmployeeStatus()));
        }).sort(Sort.by("id").descending()).limit(queryReqDTO.getPage(), queryReqDTO.getSize()));

        return new ShowPageImpl<>(page).map(entity -> {
            UserWycShopPageResDTO dto = new UserWycShopPageResDTO();
            dto.setUsername(entity.getUserEntity().getUsername());
            dto.setId(entity.getUserEntity().getId());
            dto.setJobNumber(entity.getJobNumber());
            dto.setName(entity.getUserEntity().getName());
            dto.setPhone(entity.getUserEntity().getPhone());
            List<RoleEntity> roleEntities = entity.getUserEntity().getRoleEntities();
            RoleEntity roleEntity = roleEntities.stream().filter(r -> r.getSystem().contains(OrganizationSystemEnum.wyc_shop_admin)).findFirst().get();
            dto.setRoleId(roleEntity.getId());
            dto.setRoleName(roleEntity.getName());
            dto.setUserEmployeeStatus(entity.getStatus());
            dto.setUserEmployeeStatusName(entity.getStatus().getShowName());
            UserDetailInfo userDetailInfo = userCacheData.getUserDetailInfo(entity.getCreatorId());
            dto.setCreateName(userDetailInfo.getUser().getName());
            dto.setCreateTime(entity.getCreateTime());
            return dto;
        });
    }

    @Override
    public ShowPageImpl<UserPageCompanyResDTO> findPageForOrganizationCompany(UserQueryCompanyReqDTO queryReqDTO) throws BizException {

        Page<UserEmployeeEntity> page = userEmployeeRepository.findPage(QueryCondition.from(UserEmployeeEntity.class).innerJoin("organizationEntity").innerJoin("userEntity").where(list -> {
            list.add(ConditionMap.like("organizationEntity.system", OrganizationSystemEnum.wyc_shop_admin));
            if (!StringUtils.isEmpty(queryReqDTO.getOrganizationName()))
                list.add(ConditionMap.like("organizationEntity.name", queryReqDTO.getOrganizationName()));
            if (!StringUtils.isEmpty(queryReqDTO.getUsername()))
                list.add(ConditionMap.like("userEntity.username", queryReqDTO.getUsername()));
            if (!StringUtils.isEmpty(queryReqDTO.getPhone()))
                list.add(ConditionMap.like("userEntity.phone", queryReqDTO.getPhone()));
            if (!StringUtils.isEmpty(queryReqDTO.getName()))
                list.add(ConditionMap.like("userEntity.name", queryReqDTO.getName()));
        }).sort(Sort.by("id").descending()).limit(queryReqDTO.getPage(), queryReqDTO.getSize()));

        return new ShowPageImpl<>(page).map(entity -> {
            UserPageCompanyResDTO dto = new UserPageCompanyResDTO();
            dto.setCreateTime(entity.getCreateTime());
            dto.setModifyName(userCacheData.getUserDetailInfo(entity.getModifierId()).getUser().getName());
            dto.setId(entity.getUserId());
            dto.setModifyTime(entity.getModifyTime());
            dto.setName(entity.getUserEntity().getName());
            dto.setPhone(entity.getUserEntity().getPhone());
            dto.setUsername(entity.getUserEntity().getUsername());
            dto.setOrganizationCompanyName(entity.getOrganizationEntity().getName());
            return dto;
        });
    }

    @Override
    public ShowPageImpl<UserPageAdminResDTO> findPageForAdmin(UserQueryAdminReqDTO queryReqDTO) throws BizException {

        Page<UserEmployeeEntity> page = userEmployeeRepository.findPage(QueryCondition.from(UserEmployeeEntity.class).innerJoin("organizationEntity").innerJoin("userEntity").where(list -> {
            list.add(ConditionMap.like("organizationEntity.system", OrganizationSystemEnum.xiehua_admin));
            list.add(ConditionMap.ne("userId", SSOConstant.SYSTEM_USER_ID));
            if (!StringUtils.isEmpty(queryReqDTO.getUsername()))
                list.add(ConditionMap.like("userEntity.username", queryReqDTO.getUsername()));
            if (!StringUtils.isEmpty(queryReqDTO.getPhone()))
                list.add(ConditionMap.like("userEntity.phone", queryReqDTO.getPhone()));
            if (!StringUtils.isEmpty(queryReqDTO.getName()))
                list.add(ConditionMap.like("userEntity.name", queryReqDTO.getName()));
        }).sort(Sort.by("id").descending()).limit(queryReqDTO.getPage(), queryReqDTO.getSize()));

        return new ShowPageImpl<>(page).map(entity -> {
            UserPageAdminResDTO dto = new UserPageAdminResDTO();
            dto.setCreateTime(entity.getCreateTime());
            dto.setId(entity.getUserId());
            dto.setModifyTime(entity.getModifyTime());
            dto.setName(entity.getUserEntity().getName());
            dto.setPhone(entity.getUserEntity().getPhone());
            dto.setUsername(entity.getUserEntity().getUsername());
            UserDetailInfo userDetailInfo = userCacheData.getUserDetailInfo(entity.getModifierId());
            dto.setModifyName(userDetailInfo.getUser().getName());
            return dto;
        });
    }

    /**
     * 统一创建用户
     *
     * @param userEntity
     * @param organizationEntity
     */
    private void createUser(UserEntity userEntity, OrganizationEntity organizationEntity) {
        if (CollectionUtils.isEmpty(userEntity.getThirdOpenId())) {
            userEntity.setThirdOpenId(new ArrayList<>());
        }
        userEntity.setOrganizationId(organizationEntity.getId());
        userEntity.setUserStatus(UserStatusEnum.default_status);
        userRepository.create(userEntity);
       /* //创建个人账户
        if (AccountTypeEnum.person.equals(userEntity.getUserType())) {
            SubAccountCreateReqDTO subAccountCreateReqDTO = new SubAccountCreateReqDTO();
            subAccountCreateReqDTO.setRelaId(String.valueOf(userEntity.getId()));
            subAccountCreateReqDTO.setSubAccountTypeList(Arrays.asList(SubAccountTypeEnum.tripartite));
            subAccountCreateReqDTO.setOrganizationId(organizationEntity.getId());
            subAccountCreateReqDTO.setType(AccountTypeEnum.person);
            subAccountFeignClient.createSubAccount(subAccountCreateReqDTO);
        } else if (AccountTypeEnum.company.equals(userEntity.getUserType())) {
            //创建公司资金账户
            SubAccountCreateReqDTO subAccountCreateReqDTO = new SubAccountCreateReqDTO();
            subAccountCreateReqDTO.setRelaId(String.valueOf(organizationEntity.getOrganizationCompanyEntity().getId()));
            subAccountCreateReqDTO.setSubAccountTypeList(Arrays.asList(SubAccountTypeEnum.tripartite));
            subAccountCreateReqDTO.setOrganizationId(organizationEntity.getId());
            subAccountCreateReqDTO.setType(AccountTypeEnum.company);
            subAccountFeignClient.createSubAccount(subAccountCreateReqDTO);
        }*/
    }


    @Override
    public List<EmployeeSelectDTO> employeeSeletList() {
        List<EmployeeSelectDTO> dtoList = new ArrayList<>();
        List<UserEmployeeEntity> employeeList = userEmployeeRepository.findByCondition(QueryCondition.from(UserEmployeeEntity.class).where(list -> {
            list.add(ConditionMap.eq("organizationId", userCacheData.getCurrentUserOrgId()));
            list.add(ConditionMap.eq("status", UserEmployeeStatusEnum.enabled));
        }).sort(Sort.by(Sort.Direction.DESC, "createTime")));
        for (UserEmployeeEntity employee : employeeList) {
            EmployeeSelectDTO dto = new EmployeeSelectDTO();
            dto.setUserId(employee.getUserId());
            dto.setName(userCacheData.getUserDetailInfo(employee.getUserId()).getUser().getName());
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public void logoutUserForZkUser(Long userId) throws BizException {
        //如果手机号已经存在，就关联
        UserEntity entity = userRepository.find(userId);
        UserEntity.ThirdOpenId thirdOpenId = new UserEntity.ThirdOpenId(ThirdPlatformEnum.miniapp,"", "", OrganizationSystemEnum.zk_user);
        if (entity == null) {
           throw new BizException("非法访问");
        } else {
            this.modifyUserForMiniapp(entity, thirdOpenId);
        }
    }

    @Override
    public void logoutUserForZkShop(Long userId) throws BizException {
        //如果手机号已经存在，就关联
        UserEntity entity = userRepository.find(userId);
        UserEntity.ThirdOpenId thirdOpenId = new UserEntity.ThirdOpenId(ThirdPlatformEnum.miniapp,"", "", OrganizationSystemEnum.zk_shop);
        if (entity == null) {
            throw new BizException("非法访问");
        } else {
            this.modifyUserForMiniapp(entity, thirdOpenId);
        }
    }
}
