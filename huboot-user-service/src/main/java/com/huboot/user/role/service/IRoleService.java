package com.huboot.user.role.service;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.page.ShowPageImpl;
import com.huboot.user.role.dto.RoleDetailResDTO;
import com.huboot.user.user.entity.UserEntity;
import com.huboot.user.role.dto.RoleModifyReqDTO;
import com.huboot.user.role.dto.RolePageResDTO;
import com.huboot.user.role.dto.RoleQueryReqDTO;
import com.huboot.user.role.dto.wycshop.WycShopRoleCreateReqDTO;
import com.huboot.user.role.dto.wycshop.WycShopRolePageResDTO;
import com.huboot.user.role.dto.wycshop.WycShopRoleQueryReqDTO;
import com.huboot.user.role.dto.zkshop.ZkshopRoleHoumenCreateReqDTO;
import com.huboot.user.role.dto.zkshop.ZkshopRoleModifyReqDTO;
import com.huboot.user.role.dto.zkshop.ZkshopRolePageResDTO;
import com.huboot.user.role.dto.zkshop.ZkshopRoleQueryReqDTO;

import java.util.List;

/**
 * 用户服务-角色表Service
 */
public interface IRoleService {


    void create(WycShopRoleCreateReqDTO createReqDTO) throws BizException;

    void modify(RoleModifyReqDTO modifyReqDTO) throws BizException;
    void modifyForZkshop(ZkshopRoleModifyReqDTO modifyReqDTO) throws BizException;
    void incrementRoleForZkshop(ZkshopRoleHoumenCreateReqDTO modifyReqDTO) throws BizException;
    RoleDetailResDTO find(Long id) throws BizException;

    RoleDetailResDTO findMenuForZkshopByUserId(Long userId) throws BizException;

    List<RoleDetailResDTO> findAll() throws BizException;

    void delete(Long id) throws BizException;

    ShowPageImpl<RolePageResDTO> findPage(RoleQueryReqDTO queryReqDTO) throws BizException;

    ShowPageImpl<ZkshopRolePageResDTO> findPageForZkShop(ZkshopRoleQueryReqDTO queryReqDTO) throws BizException;

    ShowPageImpl<WycShopRolePageResDTO> findPageForWycShop(WycShopRoleQueryReqDTO queryReqDTO) throws BizException;

    void createRoleForOrganizationCompany(Long userId);

    void createRoleForAdmin(Long userId);

    void createRoleForWycShop(Long userId, Long roleId);

    void createRoleForWycMiniapp(Long userId);

    //zk商家端初始化权限
    void createInitRoleForZkShop(Long userId);
    void createInitRoleForZkUser(Long userId);

    /**
     * BizConstant
     * @param userId
     * @param roleId BizConstant 中系统初始化的role
     */
    void createRoleForZkShopFormDefaultRole(Long userId,Long organizationId, Long roleId);

    //初始化直客注册的角色
    void initRoleForZkShopFormDefaultRole(Long organizationId);

    void modifyUserRoleById(UserEntity entity, Long roleId);

}
