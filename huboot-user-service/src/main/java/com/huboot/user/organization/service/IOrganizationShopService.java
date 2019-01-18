package com.huboot.user.organization.service;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.page.ShowPageImpl;
import com.huboot.share.user_service.api.dto.ShopDetaiInfo;
import com.huboot.user.organization.dto.OrganizationShopCreateReqDTO;
import com.huboot.user.organization.dto.OrganizationShopDetailResDTO;
import com.huboot.user.organization.dto.OrganizationShopPageResDTO;
import com.huboot.user.organization.dto.OrganizationShopQueryReqDTO;

import com.huboot.user.organization.dto.OrganizationShopModifyReqDTO;

/**
 *用户服务-部门店铺表Service
 */
public interface IOrganizationShopService {

    ShopDetaiInfo findDetail(Long shopId);

    ShopDetaiInfo findByShopSn(String shopSn) throws BizException;

    /**
    * 创建
    * @param createReqDTO
    * @throws BizException
    */
    void create(OrganizationShopCreateReqDTO createReqDTO) throws BizException;

    /**
    * 更新
    * @param modifyReqDTO
    * @throws BizException
    */
    void modify(OrganizationShopModifyReqDTO modifyReqDTO) throws BizException;

    /**
    * 查询
    * @param id
    * @return
    * @throws BizException
    */
    OrganizationShopDetailResDTO find(Long id) throws BizException;

    /**
    * 删除
    * @param id
    * @throws BizException
    */
    void delete(Long id) throws BizException;

    /**
    * 分页查询
    * @param queryReqDTO
    * @return
    * @throws BizException
    */
    ShowPageImpl<OrganizationShopPageResDTO> findPage(OrganizationShopQueryReqDTO queryReqDTO) throws BizException;

}
