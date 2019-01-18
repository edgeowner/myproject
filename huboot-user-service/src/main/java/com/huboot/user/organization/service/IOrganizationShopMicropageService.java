package com.huboot.user.organization.service;

import com.huboot.commons.component.exception.BizException;
import com.huboot.user.organization.dto.*;

import java.util.List;

/**
 *组织-微页面表Service
 */
public interface IOrganizationShopMicropageService {

    /**
     * 微页面推广详情
     *
     * @param sn
     * @return
     * @throws BizException
     */
    OrganizationShopMicropagePromotionDTO promotionDetail(String sn) throws BizException;

    /**
     * 经纪人推广详情
     *
     * @param sn
     * @return
     * @throws BizException
     */
    OrganizationShopMicropageAgentDTO agentDetail(String sn) throws BizException;

    /**
     * 企业介绍详情
     *
     * @param sn
     * @return
     * @throws BizException
     */
    OrganizationShopMicropageIntroductionDTO introductionDetail(String sn) throws BizException;

    /**
     * 司机福利详情
     *
     * @param sn
     * @return
     * @throws BizException
     */
    OrganizationShopMicropageWelfareDTO welfareDetail(String sn) throws BizException;

    /**
     * 车型详情
     *
     * @param sn
     * @return
     * @throws BizException
     */
    List<OrganizationShopMicropageModelDTO> modelDetail(String sn) throws BizException;

    /**
     * 入行指南详情
     *
     * @param sn
     * @return
     * @throws BizException
     */
    OrganizationShopMicropageGuideDTO guideDetail(String sn) throws BizException;

    /**
     * 联系方式详情
     *
     * @param sn
     * @return
     * @throws BizException
     */
    OrganizationShopMicropageContactDTO contactDetail(String sn) throws BizException;

    /**
     * 微页面推广保存或发布
     *
     * @param dto
     * @param type
     * @throws BizException
     */
    void promotionSaveOrPublish(OrganizationShopMicropagePromotionDTO dto, Integer type) throws BizException;

    /**
     * 经纪人推广保存或发布
     *
     * @param dto
     * @param type
     * @throws BizException
     */
    void agentSaveOrPublish(OrganizationShopMicropageAgentDTO dto, Integer type) throws BizException;

    /**
     * 企业介绍保存或发布
     *
     * @param dto
     * @param type
     * @throws BizException
     */
    void introductionSaveOrPublish(OrganizationShopMicropageIntroductionDTO dto, Integer type) throws BizException;

    /**
     * 司机福利保存或发布
     *
     * @param dto
     * @param type
     * @throws BizException
     */
    void welfareSaveOrPublish(OrganizationShopMicropageWelfareDTO dto, Integer type) throws BizException;

    /**
     * 车型详情保存或发布
     *
     * @param dtoList
     * @param type
     * @throws BizException
     */
    void modelSaveOrPublish(List<OrganizationShopMicropageModelDTO> dtoList, Integer type) throws BizException;

    /**
     * 入行指南保存或发布
     *
     * @param dto
     * @param type
     * @throws BizException
     */
    void guideSaveOrPublish(OrganizationShopMicropageGuideDTO dto, Integer type) throws BizException;

    /**
     * 联系方式保存或发布
     *
     * @param dto
     * @param type
     * @throws BizException
     */
    void contactSaveOrPublish(OrganizationShopMicropageContactDTO dto, Integer type) throws BizException;
}
