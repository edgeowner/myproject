package com.huboot.user.organization.service;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.page.ShowPageImpl;
import com.huboot.share.user_service.api.dto.CompanyDetailInfo;
import com.huboot.user.organization.dto.admin.*;
import com.huboot.user.organization.dto.zkshop.OrganizationCompanyDetailForZkShopResDTO;
import com.huboot.user.user.dto.zkshop.BusinessLicenseORCDTO;

import com.huboot.user.organization.dto.zkshop.OrganizationCompanyCreateForZkShopReqDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户服务-公司表Service
 */
public interface IOrganizationCompanyService {

    /**
     * 创建
     *
     * @param createReqDTO
     * @throws BizException
     */
    void create(OrganizationCompanyCreateReqDTO createReqDTO) throws BizException;
    void create(OrganizationCompanyCreateForZkShopReqDTO createReqDTO) throws BizException;

    /**
     * 更新
     *
     * @param modifyReqDTO
     * @throws BizException
     */
    void modify(OrganizationCompanyModifyReqDTO modifyReqDTO) throws BizException;

    /**
     * 查询
     *
     * @param id
     * @return
     * @throws BizException
     */
    OrganizationCompanyDetailResDTO find(Long id) throws BizException;

    OrganizationCompanyDetailForZkShopResDTO findForZkShop(Long id) throws BizException;

    List<OrganizationCompanyNameResDTO> findByName(String name, String abbreviation) throws BizException;

    /**
     * 删除
     *
     * @param id
     * @throws BizException
     */
    void delete(Long id) throws BizException;

    /**
     * 分页查询
     *
     * @param queryReqDTO
     * @return
     * @throws BizException
     */
    ShowPageImpl<OrganizationCompanyPageResDTO> findPage(OrganizationCompanyQueryReqDTO queryReqDTO) throws BizException;

    String generatorUsername(Long organizationId);

    /**
     * 查询所有公司
     *
     * @return
     * @throws BizException
     */
    List<CompanyDetailInfo> findAll() throws BizException;

    /**
     * 营业执照ocr识别
     *
     * @param file
     * @return
     * @throws Exception
     */
    BusinessLicenseORCDTO businessLicenseOrc(MultipartFile file) throws Exception;
}
