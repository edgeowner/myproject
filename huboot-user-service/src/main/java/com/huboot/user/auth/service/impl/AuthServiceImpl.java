package com.huboot.user.auth.service.impl;

import com.huboot.commons.component.auth.JwtClaims;
import com.huboot.commons.component.auth.JwtUser;
import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.filter.RequestInfo;
import com.huboot.commons.jpa.ConditionMap;
import com.huboot.commons.jpa.QueryCondition;
import com.huboot.commons.utils.AppAssert;
import com.huboot.commons.utils.CommonTools;
import com.huboot.share.notify_service.api.dto.SMSSendDTO;
import com.huboot.share.notify_service.api.feign.SMSFeignClient;
import com.huboot.share.user_service.api.dto.*;
import com.huboot.share.user_service.data.AreaCacheData;
import com.huboot.share.user_service.data.CompanyCacheData;
import com.huboot.share.user_service.data.ShopCacheData;
import com.huboot.share.user_service.data.UserCacheData;
import com.huboot.share.user_service.enums.OrganizationSystemEnum;
import com.huboot.share.user_service.enums.PermissionTypeEnum;
import com.huboot.share.user_service.enums.ThirdPlatformEnum;
import com.huboot.share.user_service.enums.UserEmployeeStatusEnum;
import com.huboot.user.auth.dto.*;
import com.huboot.user.auth.dto.wycminiapp.UserCustomerWeixinPhoneNumberAddReqDTO;
import com.huboot.user.auth.dto.wycminiapp.WycMiniappLoginInfoResDTO;
import com.huboot.user.auth.dto.wycshop.WycShopLoginInfoResDTO;
import com.huboot.user.auth.dto.zkshop.ZkShopLoginInfoResDTO;
import com.huboot.user.auth.dto.zkuser.ZkUserLoginInfoResDTO;
import com.huboot.user.auth.service.IAuthService;
import com.huboot.user.common.cache.UserCachePutData;
import com.huboot.user.common.enums.UserSmsEnum;
import com.huboot.user.organization.entity.OrganizationShopEntity;
import com.huboot.user.organization.repository.IOrganizationShopRepository;
import com.huboot.user.role.dto.RoleDetailResDTO;
import com.huboot.user.role.entity.RoleEntity;
import com.huboot.user.role.service.IRoleService;
import com.huboot.user.user.dto.wycdriverminiapp.UserCreateMiniAppReqDTO;
import com.huboot.user.user.dto.zkshop.UserCreateZkShopReqDTO;
import com.huboot.user.user.dto.zkuser.UserCreateZkUserReqDTO;
import com.huboot.user.user.entity.UserEntity;
import com.huboot.user.user.repository.IUserEmployeeRepository;
import com.huboot.user.user.service.IUserService;
import com.huboot.user.weixin.service.IMiniappUserService;
import com.huboot.user.auth.dto.admin.AdminLoginInfoResDTO;
import com.huboot.user.common.cache.UserDataProxy;
import com.huboot.user.common.constant.UserConstant;
import com.huboot.user.permission.entity.PermissionEntity;
import com.huboot.user.user.entity.UserEmployeeEntity;
import com.huboot.user.user.repository.IUserRepository;
import com.huboot.user.weixin.service.IMiniappService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户服务-认证ServiceImpl
 */
@Service("authServiceImpl")
public class AuthServiceImpl implements IAuthService {

    private Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IOrganizationShopRepository organizationShopRepository;
    @Autowired
    private IMiniappService miniappService;
    @Autowired
    private IMiniappUserService miniappUserService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserEmployeeRepository userEmployeeRepository;
    @Autowired
    private AreaCacheData areaCacheData;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private UserCacheData userCacheData;
    @Autowired
    private UserCachePutData userCachePutData;
    @Autowired
    private UserDataProxy userDataProxy;
    @Autowired
    private CompanyCacheData companyCacheData;
    @Autowired
    private ShopCacheData shopCacheData;
    @Autowired
    private SMSFeignClient smsFeignClient;

    @Override
    public JwtClaims loginAdmin(@Valid UsernamePasswordAuthenticationReqDTO dto) throws BizException {
        List<UserEntity> userEntityList = userRepository.findByCondition(QueryCondition.from(UserEntity.class).innerJoin("organizationEntity").where(list -> {
            list.add(ConditionMap.like("organizationEntity.system", OrganizationSystemEnum.xiehua_admin));
            list.add(ConditionMap.eq("username", dto.getUsername()));
        }).limit(1, 1));
        if (CollectionUtils.isEmpty(userEntityList)) {
            throw new BizException("用户不存在");
        }
        UserEntity userEntity = userEntityList.get(0);
        List<UserEntity.ThirdOpenId> thirdOpenId = userEntity.getThirdOpenId();
        Boolean result = passwordEncoder.matches(dto.getPassword(), userEntity.getPassword());
        if (!result) throw new BizException("用户名或密码不正确");
        JwtUser jwtUser = new JwtUser(userEntity.getId(), userEntity.getOrganizationId(), userEntity.getGuid(), userEntity.getUsername());
        JwtClaims jwtClaims = new JwtClaims(JwtClaims.Sub.User, jwtUser);
        return jwtClaims;
    }

    @Override
    public JwtClaims loginWycMiniapp(UsernamePasswordAuthenticationReqDTO dto) throws BizException {
        //拿到匿名用户的访问信息
        String shopSn = RequestInfo.getJwtUser().getShopSn();
        AppAssert.hasText(shopSn, "缺少店铺的编号shop-sn");
        OrganizationShopEntity organizationShopEntity = organizationShopRepository.findBySn(shopSn);
        UserEntity userEntity = userRepository.findByPhoneAndOrganizationId(dto.getUsername(), organizationShopEntity.getOrganizationId());
        if (userEntity == null) {
            throw new BizException("用户不存在");
        }
        Boolean result = passwordEncoder.matches(dto.getPassword(), userEntity.getPassword());
        if (!result) throw new BizException("用户名或密码不正确");
        JwtUser jwtUser = new JwtUser(userEntity.getId(), organizationShopEntity.getOrganizationId(), userEntity.getGuid(), userEntity.getUsername());
        jwtUser.setShopSn(shopSn);
        JwtClaims jwtClaims = new JwtClaims(JwtClaims.Sub.User, jwtUser);
        return jwtClaims;
    }

    @Override
    public JwtClaims loginWycShop(UsernamePasswordAuthenticationReqDTO dto) throws BizException {
        List<UserEmployeeEntity> userEmployeeEntities = userEmployeeRepository.findByCondition(
                QueryCondition.from(UserEmployeeEntity.class).innerJoin("organizationEntity").innerJoin("userEntity").where(list -> {
                    list.add(ConditionMap.like("organizationEntity.system", OrganizationSystemEnum.wyc_shop_admin));
                    list.add(ConditionMap.eq("userEntity.username", dto.getUsername()));
                }));
        if (CollectionUtils.isEmpty(userEmployeeEntities)) {
            throw new BizException("用户不存在");
        }
        UserEmployeeEntity userEmployeeEntity = userEmployeeEntities.get(0);
        Boolean result = passwordEncoder.matches(dto.getPassword(), userEmployeeEntity.getUserEntity().getPassword());
        if (!result) throw new BizException("用户名或密码不正确");
        if (userEmployeeEntity.getStatus().equals(UserEmployeeStatusEnum.disable)) throw new BizException("用户已被禁用");
        JwtUser jwtUser = new JwtUser(userEmployeeEntity.getUserEntity().getId(), userEmployeeEntity.getUserEntity().getOrganizationId(), userEmployeeEntity.getUserEntity().getGuid(), userEmployeeEntity.getUserEntity().getUsername());
        JwtClaims jwtClaims = new JwtClaims(JwtClaims.Sub.User, jwtUser);
        return jwtClaims;
    }

    @Override
    public JwtClaims wxLogin(ThirdAuthenticationReqDTO dto) throws BizException {
        String shopSn = RequestInfo.getJwtUser().getShopSn();
        AppAssert.hasText(shopSn, "缺少店铺的编号shop-sn");
        OrganizationShopEntity organizationShopEntity = organizationShopRepository.findBySn(shopSn);
        if (ThirdPlatformEnum.miniapp.equals(dto.getPlatform())) {
            UserAuthResultDTO userAuthResultDTO = miniappService.getOpenId(organizationShopEntity.getId(), dto.getCode());
            UserEntity.ThirdOpenId thirdOpenId = new UserEntity.ThirdOpenId(ThirdPlatformEnum.miniapp, userAuthResultDTO.getAppId(), userAuthResultDTO.getOpenId(), OrganizationSystemEnum.wyc_driver_miniapp);
            QueryCondition queryCondition = QueryCondition.from(UserEntity.class).where(list -> {
                list.add(ConditionMap.like("thirdOpenId", thirdOpenId.getValue()));
                list.add(ConditionMap.like("thirdOpenId", thirdOpenId.getThirdName().toString()));
            }).limit(1, 1);
            List<UserEntity> userEntityList = userRepository.findByCondition(queryCondition);
            if (!CollectionUtils.isEmpty(userEntityList)) {
                UserEntity userEntity = userEntityList.get(0);
                JwtUser jwtUser = new JwtUser(userEntity.getId(), userEntity.getOrganizationId(), userEntity.getGuid(), userEntity.getUsername());
                jwtUser.setShopSn(shopSn);
                JwtClaims jwtClaims = new JwtClaims(JwtClaims.Sub.User, jwtUser);
                return jwtClaims;
            }
        } else {
            throw new BizException("微信授权失败，参数传入的平台错误");
        }
        return null;
    }

    @Override
    public JwtClaims wxLoginForZkShop(ThirdAuthenticationReqDTO dto) throws BizException {
        if (ThirdPlatformEnum.miniapp.equals(dto.getPlatform())) {
            //登陆平台的小程序
            OrganizationShopEntity organizationShopEntity = organizationShopRepository.find(UserConstant.ORGANIZATION_SHOP_ID_PLATFORM);
            //查询用户是否注册
            UserAuthResultDTO userAuthResultDTO = miniappService.getOpenId(organizationShopEntity.getId(), dto.getCode());
            UserEntity.ThirdOpenId thirdOpenId = new UserEntity.ThirdOpenId(ThirdPlatformEnum.miniapp, userAuthResultDTO.getAppId(), userAuthResultDTO.getOpenId(), OrganizationSystemEnum.zk_shop);
//            String thirdOpenIdString = JsonUtil.buildNormalMapperWithDefaultTyping().toJson(thirdOpenId);
            List<UserEntity> userEntityList = userRepository.findByCondition(QueryCondition.from(UserEntity.class).where(list -> {
                list.add(ConditionMap.like("thirdOpenId", thirdOpenId.getValue()));
                list.add(ConditionMap.like("thirdOpenId", thirdOpenId.getAppId()));
                list.add(ConditionMap.like("thirdOpenId", thirdOpenId.getThirdName().toString()));
                list.add(ConditionMap.like("thirdOpenId", thirdOpenId.getSystem().toString()));
            }).limit(1, 1));
            if (!CollectionUtils.isEmpty(userEntityList)) {
                UserEntity userEntity = userEntityList.get(0);
                JwtUser jwtUser = new JwtUser(userEntity.getId(), userEntity.getOrganizationId(), userEntity.getGuid(), userEntity.getUsername());
                jwtUser.setShopSn(organizationShopEntity.getSn());
                JwtClaims jwtClaims = new JwtClaims(JwtClaims.Sub.User, jwtUser);
                return jwtClaims;
            }
        } else {
            throw new BizException("微信授权失败，参数传入的平台错误");
        }
        return null;
    }

    @Override
    public JwtClaims wxLoginForZkUser(ThirdAuthenticationReqDTO dto) throws BizException {
        if (ThirdPlatformEnum.miniapp.equals(dto.getPlatform())) {
            //登陆平台的小程序
            OrganizationShopEntity organizationShopEntity = organizationShopRepository.find(UserConstant.ORGANIZATION_USER_ID_PLATFORM);
            //查询用户是否注册
            UserAuthResultDTO userAuthResultDTO = miniappService.getOpenId(organizationShopEntity.getId(), dto.getCode());
            UserEntity.ThirdOpenId thirdOpenId = new UserEntity.ThirdOpenId(ThirdPlatformEnum.miniapp, userAuthResultDTO.getAppId(), userAuthResultDTO.getOpenId(), OrganizationSystemEnum.zk_user);
//            String thirdOpenIdString = JsonUtil.buildNormalMapperWithDefaultTyping().toJson(thirdOpenId);
            List<UserEntity> userEntityList = userRepository.findByCondition(QueryCondition.from(UserEntity.class).where(list -> {
                list.add(ConditionMap.like("thirdOpenId", thirdOpenId.getValue()));
                list.add(ConditionMap.like("thirdOpenId", thirdOpenId.getAppId()));
                list.add(ConditionMap.like("thirdOpenId", thirdOpenId.getThirdName().toString()));
                list.add(ConditionMap.like("thirdOpenId", thirdOpenId.getSystem().toString()));
            }).limit(1, 1));
            if (!CollectionUtils.isEmpty(userEntityList)) {
                UserEntity userEntity = userEntityList.get(0);
                JwtUser jwtUser = new JwtUser(userEntity.getId(), userEntity.getOrganizationId(), userEntity.getGuid(), userEntity.getUsername());
                jwtUser.setShopSn(organizationShopEntity.getSn());
                JwtClaims jwtClaims = new JwtClaims(JwtClaims.Sub.User, jwtUser);
                return jwtClaims;
            }
        } else {
            throw new BizException("微信授权失败，参数传入的平台错误");
        }
        return null;
    }

    @Override
    public JwtClaims weinxinPhoneNumber(@Valid UserCustomerWeixinPhoneNumberAddReqDTO dto) throws BizException {
        //拿到匿名用户的访问信息
        String shopSn = RequestInfo.getJwtUser().getShopSn();
        AppAssert.hasText(shopSn, "缺少店铺的编号shop-sn");
        OrganizationShopEntity organizationShopEntity = organizationShopRepository.findBySn(shopSn);
        //查询用户是否注册
        UserAuthResultDTO userAuthResultDTO = miniappService.getOpenId(organizationShopEntity.getId(), dto.getCode());
        String phone = miniappUserService.getPhoneNumber(dto.getEncryptedData(), dto.getIv(), userAuthResultDTO.getOpenId());
        if (!StringUtils.isEmpty(phone)) {
            //注册/登陆
            UserCreateMiniAppReqDTO userCreateMiniAppReqDTO = new UserCreateMiniAppReqDTO();
            userCreateMiniAppReqDTO.setPhone(phone);
            userCreateMiniAppReqDTO.setOrganizationId(organizationShopEntity.getOrganizationId());
            userCreateMiniAppReqDTO.setOpenId(userAuthResultDTO.getOpenId());
            userCreateMiniAppReqDTO.setAppId(userAuthResultDTO.getAppId());
            UserEntity userEntity = userService.createOrModifyUserForWycMiniapp(userCreateMiniAppReqDTO);
            JwtUser jwtUser = new JwtUser(userEntity.getId(), organizationShopEntity.getOrganizationId(), userEntity.getGuid(), userEntity.getUsername());
            jwtUser.setShopSn(shopSn);
            JwtClaims jwtClaims = new JwtClaims(JwtClaims.Sub.User, jwtUser);
            return jwtClaims;
        }
        return null;
    }

    @Override
    public JwtClaims weinxinPhoneNumberForZkShop(@Valid UserCustomerWeixinPhoneNumberAddReqDTO dto) throws BizException {
        //登陆平台的小程序
        OrganizationShopEntity organizationShopEntity = organizationShopRepository.find(UserConstant.ORGANIZATION_SHOP_ID_PLATFORM);
        //查询用户是否注册
        UserAuthResultDTO userAuthResultDTO = miniappService.getOpenId(organizationShopEntity.getId(), dto.getCode());
        String phone = miniappUserService.getPhoneNumber(dto.getEncryptedData(), dto.getIv(), userAuthResultDTO.getOpenId());
        if (!StringUtils.isEmpty(phone)) {
            //注册/登陆
            UserCreateZkShopReqDTO userCreateZkShopReqDTO = new UserCreateZkShopReqDTO();
            userCreateZkShopReqDTO.setPhone(phone);
            userCreateZkShopReqDTO.setOrganizationId(organizationShopEntity.getOrganizationId());
            userCreateZkShopReqDTO.setOpenId(userAuthResultDTO.getOpenId());
            userCreateZkShopReqDTO.setAppId(userAuthResultDTO.getAppId());
            UserEntity userEntity = userService.createOrModifyUserForZkShop(userCreateZkShopReqDTO);
            JwtUser jwtUser = new JwtUser(userEntity.getId(), organizationShopEntity.getOrganizationId(), userEntity.getGuid(), userEntity.getUsername());
            //暂定这个逻辑ShopSn取系统ShopSn
            jwtUser.setShopSn(organizationShopEntity.getSn());
            JwtClaims jwtClaims = new JwtClaims(JwtClaims.Sub.User, jwtUser);
            return jwtClaims;
        }
        return null;
    }

    @Override
    public JwtClaims weinxinPhoneNumberForZkUser(@Valid UserCustomerWeixinPhoneNumberAddReqDTO dto) throws BizException {
        //登陆平台的小程序
        OrganizationShopEntity organizationShopEntity = organizationShopRepository.find(UserConstant.ORGANIZATION_USER_ID_PLATFORM);
        //查询用户是否注册--应用程序序列化的json和数据库序列化的json排序不同，就算应用中指定了排序，数据库也不存储不同（数据库的排序还没研究）
        UserAuthResultDTO userAuthResultDTO = miniappService.getOpenId(organizationShopEntity.getId(), dto.getCode());
        String phone = miniappUserService.getPhoneNumber(dto.getEncryptedData(), dto.getIv(), userAuthResultDTO.getOpenId());
        if (!StringUtils.isEmpty(phone)) {
            //注册/登陆
            UserCreateZkUserReqDTO userCreateZkUserReqDTO = new UserCreateZkUserReqDTO();
            userCreateZkUserReqDTO.setPhone(phone);
            userCreateZkUserReqDTO.setOrganizationId(organizationShopEntity.getOrganizationId());
            userCreateZkUserReqDTO.setOpenId(userAuthResultDTO.getOpenId());
            userCreateZkUserReqDTO.setAppId(userAuthResultDTO.getAppId());
            UserEntity userEntity = userService.createOrModifyUserForZkUser(userCreateZkUserReqDTO);
            JwtUser jwtUser = new JwtUser(userEntity.getId(), organizationShopEntity.getOrganizationId(), userEntity.getGuid(), userEntity.getUsername());
            //暂定这个逻辑ShopSn取系统ShopSn
            jwtUser.setShopSn(organizationShopEntity.getSn());
            JwtClaims jwtClaims = new JwtClaims(JwtClaims.Sub.User, jwtUser);
            return jwtClaims;
        }
        return null;
    }

    @Override
    public void updatePassword(UpdatePasswordReqDTO dto) throws BizException {
        JwtUser jwtUser = RequestInfo.getJwtUser();
        UserEntity userEntity = userRepository.find(Long.valueOf(jwtUser.getUserId()));
        AppAssert.notNull(userEntity, "用户不存在");
        Boolean result = passwordEncoder.matches(dto.getOldPassword(), userEntity.getPassword());
        if (!result) throw new BizException("旧密码不正确");
        String newPassword = passwordEncoder.encode(dto.getNewPassword());
        userEntity.setPassword(newPassword);
        userRepository.modify(userEntity);
    }

    @Override
    public AdminLoginInfoResDTO findAdminLoginInfo() throws BizException {
        JwtUser jwtUser = RequestInfo.getJwtUser();
        UserEntity userEntity = userRepository.find(Long.valueOf(jwtUser.getUserId()));
        AdminLoginInfoResDTO userLoginInfoResDTO = new AdminLoginInfoResDTO();
        userLoginInfoResDTO.setUserId(userEntity.getId());
        userLoginInfoResDTO.setName(userEntity.getName());
        userLoginInfoResDTO.setPhone(userEntity.getPhone());
        userLoginInfoResDTO.setUsername(userEntity.getUsername());
        return userLoginInfoResDTO;
    }

    @Override
    public WycMiniappLoginInfoResDTO findWycMiniAppLoginInfo() throws BizException {
        UserDetailInfo userDetailInfo = userCacheData.getCurrentUser();
        WycMiniappLoginInfoResDTO userLoginInfoResDTO = new WycMiniappLoginInfoResDTO();
        userLoginInfoResDTO.setUserId(userDetailInfo.getUser().getUserId());
        userLoginInfoResDTO.setName(userDetailInfo.getUser().getName());
        userLoginInfoResDTO.setPhone(userDetailInfo.getUser().getPhone());
        userLoginInfoResDTO.setUsername(userDetailInfo.getUser().getUsername());
        return userLoginInfoResDTO;
    }

    @Override
    public ZkShopLoginInfoResDTO findZkShopLoginInfo() throws BizException {
        UserDetailInfo userDetailInfo = userCacheData.getCurrentUser();
        ZkShopLoginInfoResDTO userLoginInfoResDTO = new ZkShopLoginInfoResDTO();
        userLoginInfoResDTO.setUserId(userDetailInfo.getUser().getUserId());
        userLoginInfoResDTO.setName(userDetailInfo.getUser().getName());
        userLoginInfoResDTO.setPhone(userDetailInfo.getUser().getPhone());
        userLoginInfoResDTO.setUsername(userDetailInfo.getUser().getUsername());
        if (userDetailInfo.getUserEmployee() != null) {
            if (userDetailInfo.getUserEmployee().getCompanyId() != null) {
                CompanyDetailInfo companyDetailInfo = companyCacheData.getById(userDetailInfo.getUserEmployee().getCompanyId());
                userLoginInfoResDTO.setAuditStatus(companyDetailInfo.getAuditStatus());
            }
            if (userDetailInfo.getUserEmployee().getZkShopId() != null) {
                userLoginInfoResDTO.setShopId(userDetailInfo.getUserEmployee().getZkShopId());
                ShopDetaiInfo shopDetaiInfo = shopCacheData.getShopById(userDetailInfo.getUserEmployee().getZkShopId());
                userLoginInfoResDTO.setShopName(shopDetaiInfo.getName());
                AreaDTO areaDTO = areaCacheData.getById(shopDetaiInfo.getAreaId());
                AreaDTO rootAreaDTO = areaCacheData.getById(areaDTO.getPath().get(0));
                userLoginInfoResDTO.setAreaShortName(rootAreaDTO.getShortName());
            }
        }
        //菜单列表
        List<String> permission;
        RoleDetailResDTO roleDetailResDTOS = roleService.findMenuForZkshopByUserId(userLoginInfoResDTO.getUserId());
        if (roleDetailResDTOS == null) {
            permission = new ArrayList<>();
        } else {
            permission = roleDetailResDTOS.getPermission();
        }
        userLoginInfoResDTO.setPermission(permission);
        return userLoginInfoResDTO;
    }

    @Override
    public ZkUserLoginInfoResDTO findZkUserLoginInfo() throws BizException {
        UserDetailInfo userDetailInfo = userCacheData.getCurrentUser();
        ZkUserLoginInfoResDTO userLoginInfoResDTO = new ZkUserLoginInfoResDTO();
        userLoginInfoResDTO.setUserId(userDetailInfo.getUser().getUserId());
        userLoginInfoResDTO.setName(userDetailInfo.getUser().getName());
        userLoginInfoResDTO.setPhone(userDetailInfo.getUser().getPhone());
        userLoginInfoResDTO.setUsername(userDetailInfo.getUser().getUsername());
        return userLoginInfoResDTO;
    }

    @Override
    public WycShopLoginInfoResDTO findWycShopLoginInfo() throws BizException {
        JwtUser jwtUser = RequestInfo.getJwtUser();
        AppAssert.notNull(jwtUser.getUserId(), "用户未登录");
        UserEntity userEntity = userRepository.find(Long.valueOf(jwtUser.getUserId()));
        WycShopLoginInfoResDTO userLoginInfoResDTO = new WycShopLoginInfoResDTO();
        userLoginInfoResDTO.setUserId(userEntity.getId());
        userLoginInfoResDTO.setName(userEntity.getName());
        userLoginInfoResDTO.setPhone(userEntity.getPhone());
        userLoginInfoResDTO.setUsername(userEntity.getUsername());
        userLoginInfoResDTO.setOrganizationCompanyName(userEntity.getOrganizationEntity().getName());
        //查询资源
        List<RoleEntity> roleEntities = userEntity.getRoleEntities();
        RoleEntity roleEntity = roleEntities.stream().filter(r -> r.getSystem().contains(OrganizationSystemEnum.wyc_shop_admin)).findFirst().get();
        PermissionEntity permissionEntity = roleEntity.getPermissionEntities().stream().filter(p -> p.getType().equals(PermissionTypeEnum.Menu)).findFirst().get();
        userLoginInfoResDTO.setPermission(permissionEntity.getResourcesUrl());
        return userLoginInfoResDTO;
    }

    @Override
    public JwtClaims loginZkshop(@Valid UsernameCodeAuthenticationReqDTO dto) throws BizException {
        //校验验证码
        String smsCode = userDataProxy.getRandomCodeForZkShop(dto.getUsername());
        if (!dto.getSmsCode().equals(smsCode)) {
            throw new BizException("短信验证码错误");
        }
        userCachePutData.clearCodeForZkShop(dto.getUsername());
        //登陆平台的小程序
        OrganizationShopEntity organizationShopEntity = organizationShopRepository.find(UserConstant.ORGANIZATION_SHOP_ID_PLATFORM);
        //查询用户是否注册
        UserAuthResultDTO userAuthResultDTO = miniappService.getOpenId(organizationShopEntity.getId(), dto.getCode());
        //注册/登陆
        UserCreateZkShopReqDTO userCreateZkShopReqDTO = new UserCreateZkShopReqDTO();
        userCreateZkShopReqDTO.setPhone(dto.getUsername());
        userCreateZkShopReqDTO.setOrganizationId(organizationShopEntity.getOrganizationId());
        userCreateZkShopReqDTO.setOpenId(userAuthResultDTO.getOpenId());
        userCreateZkShopReqDTO.setAppId(userAuthResultDTO.getAppId());
        UserEntity userEntity = userService.createOrModifyUserForZkShop(userCreateZkShopReqDTO);
        JwtUser jwtUser = new JwtUser(userEntity.getId(), organizationShopEntity.getOrganizationId(), userEntity.getGuid(), userEntity.getUsername());
        //暂定这个逻辑ShopSn取系统ShopSn
        jwtUser.setShopSn(organizationShopEntity.getSn());
        JwtClaims jwtClaims = new JwtClaims(JwtClaims.Sub.User, jwtUser);
        return jwtClaims;
    }

    @Override
    public JwtClaims loginZkUser(UsernameCodeAuthenticationReqDTO dto) throws BizException {
        //校验验证码
        String smsCode = userDataProxy.getRandomCodeForZkUser(dto.getUsername());
        if (!dto.getSmsCode().equals(smsCode)) {
            throw new BizException("短信验证码错误");
        }
        userCachePutData.clearCodeForZkUser(dto.getUsername());
        //登陆平台的小程序
        OrganizationShopEntity organizationShopEntity = organizationShopRepository.find(UserConstant.ORGANIZATION_USER_ID_PLATFORM);
        //查询用户是否注册
        UserAuthResultDTO userAuthResultDTO = miniappService.getOpenId(organizationShopEntity.getId(), dto.getCode());
        //注册/登陆
        UserCreateZkUserReqDTO userCreateZkUserReqDTO = new UserCreateZkUserReqDTO();
        userCreateZkUserReqDTO.setPhone(dto.getUsername());
        userCreateZkUserReqDTO.setOrganizationId(organizationShopEntity.getOrganizationId());
        userCreateZkUserReqDTO.setOpenId(userAuthResultDTO.getOpenId());
        userCreateZkUserReqDTO.setAppId(userAuthResultDTO.getAppId());
        UserEntity userEntity = userService.createOrModifyUserForZkUser(userCreateZkUserReqDTO);
        JwtUser jwtUser = new JwtUser(userEntity.getId(), organizationShopEntity.getOrganizationId(), userEntity.getGuid(), userEntity.getUsername());
        //暂定这个逻辑ShopSn取系统ShopSn
        jwtUser.setShopSn(organizationShopEntity.getSn());
        JwtClaims jwtClaims = new JwtClaims(JwtClaims.Sub.User, jwtUser);
        return jwtClaims;
    }

    @Override
    public void logoutZkshop() throws BizException {
        UserDetailInfo userDetailInfo = userCacheData.getCurrentUser();
        userService.logoutUserForZkShop(userDetailInfo.getUser().getUserId());
    }

    @Override
    public void logoutZkUser() throws BizException {
        UserDetailInfo userDetailInfo = userCacheData.getCurrentUser();
        userService.logoutUserForZkUser(userDetailInfo.getUser().getUserId());
    }

    @Override
    public void smsSendZkshop(UsernameCodeSendReqDTO dto) throws BizException {
        SMSSendDTO smsSendDTO = new SMSSendDTO();
        if (!CommonTools.checkMobile(dto.getUsername())) {
            throw new BizException("请输入正确的手机号");
        }
        String code = userDataProxy.randomCodeForZkShop(dto.getUsername());
        String content = MessageFormat.format(UserSmsEnum.login.getShowName(), code);
        smsSendDTO.setContent(content);
        smsSendDTO.getPhoneList().add(dto.getUsername());
        smsFeignClient.sendNoticeSMS(smsSendDTO);
    }

    @Override
    public void smsSendZkUser(UsernameCodeSendReqDTO dto) throws BizException {
        SMSSendDTO smsSendDTO = new SMSSendDTO();
        if (!CommonTools.checkMobile(dto.getUsername())) {
            throw new BizException("请输入正确的手机号");
        }
        String code = userDataProxy.randomCodeForZkUser(dto.getUsername());
        String content = MessageFormat.format(UserSmsEnum.login.getShowName(), code);
        smsSendDTO.setContent(content);
        smsSendDTO.getPhoneList().add(dto.getUsername());
        smsFeignClient.sendNoticeSMS(smsSendDTO);
    }
}
