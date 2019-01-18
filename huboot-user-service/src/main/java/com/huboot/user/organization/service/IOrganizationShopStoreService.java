package com.huboot.user.organization.service;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.page.ShowPageImpl;
import com.huboot.user.organization.dto.zkshop.ZkshopOrganizationShopStoreCreateReqDTO;
import com.huboot.user.organization.dto.zkshop.ZkshopOrganizationShopStoreModifyReqDTO;
import com.huboot.user.organization.dto.zkshop.ZkshopOrganizationShopStorePageResDTO;
import com.huboot.user.organization.dto.zkshop.ZkshopOrganizationShopStoreQueryReqDTO;
import com.huboot.user.organization.dto.zkshop.ZkshopOrganizationShopStoreDetailResDTO;

/**
 *组织-店铺门店表Service
 */
public interface IOrganizationShopStoreService {

    /**
    * 创建
    * @param createReqDTO
    * @throws BizException
    */
    void create(ZkshopOrganizationShopStoreCreateReqDTO createReqDTO) throws BizException;

    /**
    * 更新
    * @param modifyReqDTO
    * @throws BizException
    */
    void modify(ZkshopOrganizationShopStoreModifyReqDTO modifyReqDTO) throws BizException;
    void modifyDefaultStatus(Long id) throws BizException;

    /**
    * 查询
    * @param id
    * @return
    * @throws BizException
    */
    ZkshopOrganizationShopStoreDetailResDTO find(Long id) throws BizException;

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
    ShowPageImpl<ZkshopOrganizationShopStorePageResDTO> findPage(ZkshopOrganizationShopStoreQueryReqDTO queryReqDTO) throws BizException;

}
