package com.huboot.user.user.service;


import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.page.ShowPageImpl;
import com.huboot.share.user_service.api.dto.UserDetailInfo;
import com.huboot.share.user_service.enums.UserEmployeeStatusEnum;
import com.huboot.user.user.dto.UserDetailDTO;
import com.huboot.user.user.dto.UserModifyReqDTO;
import com.huboot.user.user.dto.UserPageResDTO;
import com.huboot.user.user.dto.UserQueryReqDTO;
import com.huboot.user.user.dto.admin.*;
import com.huboot.user.user.dto.wycdriverminiapp.UserCreateMiniAppReqDTO;
import com.huboot.user.user.dto.wycshop.*;
import com.huboot.user.user.dto.zkshop.UserCreateZkShopReqDTO;
import com.huboot.user.user.dto.zkuser.UserCreateZkUserReqDTO;
import com.huboot.user.user.entity.UserEntity;

import java.util.List;

/**
 * 用户服务-用户基础信息表Service
 */
public interface IUserService {

    List<Long> getUserIdListCondition(String name, String phone, String idcard);


    /**
     * 创建
     *
     * @param createReqDTO
     * @throws BizException
     */
    void createAdmin(UserCreateAdminReqDTO createReqDTO) throws BizException;

    void createOrganizationCompany(UserCreateCompanyReqDTO createReqDTO) throws BizException;

    void createWycShop(WycShopCreateCompanyReqDTO createReqDTO) throws BizException;

    void modifyWycShop(WycShopModifyCompanyReqDTO createReqDTO) throws BizException;

    void modifyWycShopStatus(Long userId, UserEmployeeStatusEnum status) throws BizException;

    UserEntity createOrModifyUserForWycMiniapp(UserCreateMiniAppReqDTO createReqDTO) throws BizException;

    UserEntity createOrModifyUserForZkShop(UserCreateZkShopReqDTO createReqDTO) throws BizException;

    UserEntity createOrModifyUserForZkUser(UserCreateZkUserReqDTO createReqDTO) throws BizException;

    void logoutUserForZkUser(Long userId) throws BizException;

    void logoutUserForZkShop(Long userId) throws BizException;

    void userInfoToRedis();

    void createPermission(PermissionCreateReqDTO wycShopRoleCreateReqDTO);

    /**
     * 更新
     *
     * @param modifyReqDTO
     * @throws BizException
     */
    void modify(UserModifyReqDTO modifyReqDTO) throws BizException;

    void modifyOrganizationCompany(UserModifyCompanyReqDTO modifyReqDTO) throws BizException;

    void modifyAdmin(UserModifyAdminReqDTO modifyReqDTO) throws BizException;

    WycShopUserDetailResDTO findForWycShop(Long id) throws BizException;

    UserDetailInfo findForInner(Long id) throws BizException;

    List<String> getUserRole(Long userId) throws BizException;

    UserDetailCompanyResDTO findCompany(Long id) throws BizException;

    UserDetailAdminResDTO findAdmin(Long id) throws BizException;

    /**
     * 删除
     *
     * @param id
     * @throws BizException
     */
    void delete(Long id) throws BizException;

    /**
     * 查询
     *
     * @return
     * @throws BizException
     */
    UserDetailDTO findDetail() throws BizException;

    /**
     * 分页查询
     *
     * @param queryReqDTO
     * @return
     * @throws BizException
     */
    ShowPageImpl<UserPageResDTO> findPage(UserQueryReqDTO queryReqDTO) throws BizException;

    ShowPageImpl<UserWycShopPageResDTO> findPageForWycShop(UserQueryWycShopReqDTO queryReqDTO) throws BizException;

    ShowPageImpl<UserPageCompanyResDTO> findPageForOrganizationCompany(UserQueryCompanyReqDTO queryReqDTO) throws BizException;

    ShowPageImpl<UserPageAdminResDTO> findPageForAdmin(UserQueryAdminReqDTO queryReqDTO) throws BizException;

    List<EmployeeSelectDTO> employeeSeletList();
}
