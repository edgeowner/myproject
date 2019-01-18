package com.huboot.user.organization.service.impl;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.utils.JsonUtil;
import com.huboot.share.user_service.data.UserCacheData;
import com.huboot.user.organization.dto.*;
import com.huboot.user.organization.entity.OrganizationShopEntity;
import com.huboot.user.organization.entity.OrganizationShopMicropageEntity;
import com.huboot.user.organization.repository.IOrganizationShopMicropageRepository;
import com.huboot.user.organization.repository.IOrganizationShopRepository;
import com.huboot.user.organization.service.IOrganizationShopMicropageService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 组织-微页面表ServiceImpl
 */
@Service("organizationShopMicropageServiceImpl")
public class OrganizationShopMicropageServiceImpl implements IOrganizationShopMicropageService {

    private Logger logger = LoggerFactory.getLogger(OrganizationShopMicropageServiceImpl.class);

    @Autowired
    private IOrganizationShopMicropageRepository organizationShopMicropageRepository;
    @Autowired
    private IOrganizationShopRepository organizationShopRepository;
    @Autowired
    private UserCacheData userCacheData;

    @Override
    public OrganizationShopMicropagePromotionDTO promotionDetail(String sn) throws BizException {
        OrganizationShopMicropageEntity entity = getMicropageInfo(sn);
        if (!StringUtils.isEmpty(null == sn ? entity.getPromotionRc() : entity.getPromotionRelease())) {
            return JsonUtil.buildNormalMapper().fromJson(null == sn ? entity.getPromotionRc() : entity.getPromotionRelease(), OrganizationShopMicropagePromotionDTO.class);
        }
        return null;
    }

    @Override
    public OrganizationShopMicropageAgentDTO agentDetail(String sn) throws BizException {
        OrganizationShopMicropageEntity entity = getMicropageInfo(sn);
        if (!StringUtils.isEmpty(null == sn ? entity.getAgentRc() : entity.getAgentRelease())) {
            return JsonUtil.buildNormalMapper().fromJson(null == sn ? entity.getAgentRc() : entity.getAgentRelease(), OrganizationShopMicropageAgentDTO.class);
        }
        return null;
    }

    @Override
    public OrganizationShopMicropageIntroductionDTO introductionDetail(String sn) throws BizException {
        OrganizationShopMicropageEntity entity = getMicropageInfo(sn);
        if (!StringUtils.isEmpty(null == sn ? entity.getIntroductionRc() : entity.getIntroductionRelease())) {
            return JsonUtil.buildNormalMapper().fromJson(null == sn ? entity.getIntroductionRc() : entity.getIntroductionRelease(), OrganizationShopMicropageIntroductionDTO.class);
        }
        return null;
    }

    @Override
    public OrganizationShopMicropageWelfareDTO welfareDetail(String sn) throws BizException {
        OrganizationShopMicropageEntity entity = getMicropageInfo(sn);
        if (!StringUtils.isEmpty(null == sn ? entity.getWelfareRc() : entity.getWelfareRelease())) {
            return JsonUtil.buildNormalMapper().fromJson(null == sn ? entity.getWelfareRc() : entity.getWelfareRelease(), OrganizationShopMicropageWelfareDTO.class);
        }
        return null;
    }

    @Override
    public List<OrganizationShopMicropageModelDTO> modelDetail(String sn) throws BizException {
        OrganizationShopMicropageEntity entity = getMicropageInfo(sn);
        if (!StringUtils.isEmpty(null == sn ? entity.getModelRc() : entity.getModelRelease())) {
            return JsonUtil.buildNormalMapper().fromJsonToList(null == sn ? entity.getModelRc() : entity.getModelRelease(), OrganizationShopMicropageModelDTO.class);
        }
        return null;
    }

    @Override
    public OrganizationShopMicropageGuideDTO guideDetail(String sn) throws BizException {
        OrganizationShopMicropageEntity entity = getMicropageInfo(sn);
        if (!StringUtils.isEmpty(null == sn ? entity.getGuideRc() : entity.getGuideRelease())) {
            return JsonUtil.buildNormalMapper().fromJson(null == sn ? entity.getGuideRc() : entity.getGuideRelease(), OrganizationShopMicropageGuideDTO.class);
        }
        return null;
    }

    @Override
    public OrganizationShopMicropageContactDTO contactDetail(String sn) throws BizException {
        OrganizationShopMicropageEntity organizationShopMicropageEntity = getMicropageInfo(sn);
        OrganizationShopMicropageContactDTO dto = new OrganizationShopMicropageContactDTO();
        if (!StringUtils.isEmpty(null == sn ? organizationShopMicropageEntity.getContactRc() : organizationShopMicropageEntity.getContactRelease())) {
            dto = JsonUtil.buildNormalMapper().fromJson(null == sn ? organizationShopMicropageEntity.getContactRc() : organizationShopMicropageEntity.getContactRelease(), OrganizationShopMicropageContactDTO.class);
        }
        //获取店铺名称
        if (!StringUtils.isEmpty(sn)) {
            OrganizationShopEntity organizationShopEntity = organizationShopRepository.findBySn(sn);
            dto.setShopName(organizationShopEntity.getName());
        }
        return dto;
    }

    @Override
    @Transactional
    public void promotionSaveOrPublish(OrganizationShopMicropagePromotionDTO dto, Integer type) throws BizException {
        OrganizationShopMicropageEntity entity = getMicropageInfo(null);
        if (type == 0) {//仅保存
            entity.setPromotionRc(JsonUtil.toNormalJson(dto));
        } else if (type == 1) {//保存并发布
            entity.setPromotionRc(JsonUtil.toNormalJson(dto));
            entity.setPromotionRelease(JsonUtil.toNormalJson(dto));
        }
        organizationShopMicropageRepository.modifyAndFlush(entity);
    }

    @Override
    @Transactional
    public void agentSaveOrPublish(OrganizationShopMicropageAgentDTO dto, Integer type) throws BizException {
        OrganizationShopMicropageEntity entity = getMicropageInfo(null);
        if (type == 0) {//仅保存
            entity.setAgentRc(JsonUtil.toNormalJson(dto));
        } else if (type == 1) {//保存并发布
            entity.setAgentRc(JsonUtil.toNormalJson(dto));
            entity.setAgentRelease(JsonUtil.toNormalJson(dto));
        }
        organizationShopMicropageRepository.modifyAndFlush(entity);
    }

    @Override
    public void introductionSaveOrPublish(OrganizationShopMicropageIntroductionDTO dto, Integer type) throws BizException {
        OrganizationShopMicropageEntity entity = getMicropageInfo(null);
        if (type == 0) {//仅保存
            entity.setIntroductionRc(JsonUtil.toNormalJson(dto));
        } else if (type == 1) {//保存并发布
            entity.setIntroductionRc(JsonUtil.toNormalJson(dto));
            entity.setIntroductionRelease(JsonUtil.toNormalJson(dto));
        }
        organizationShopMicropageRepository.modifyAndFlush(entity);
    }

    @Override
    public void welfareSaveOrPublish(OrganizationShopMicropageWelfareDTO dto, Integer type) throws BizException {
        OrganizationShopMicropageEntity entity = getMicropageInfo(null);
        if (type == 0) {//仅保存
            entity.setWelfareRc(JsonUtil.toNormalJson(dto));
        } else if (type == 1) {//保存并发布
            entity.setWelfareRc(JsonUtil.toNormalJson(dto));
            entity.setWelfareRelease(JsonUtil.toNormalJson(dto));
        }
        organizationShopMicropageRepository.modifyAndFlush(entity);
    }

    @Override
    public void modelSaveOrPublish(List<OrganizationShopMicropageModelDTO> dtoList, Integer type) throws BizException {
        OrganizationShopMicropageEntity entity = getMicropageInfo(null);
        if (type == 0) {//仅保存
            entity.setModelRc(JsonUtil.toNormalJson(dtoList));
        } else if (type == 1) {//保存并发布
            entity.setModelRc(JsonUtil.toNormalJson(dtoList));
            entity.setModelRelease(JsonUtil.toNormalJson(dtoList));
        }
        organizationShopMicropageRepository.modifyAndFlush(entity);
    }

    @Override
    public void guideSaveOrPublish(OrganizationShopMicropageGuideDTO dto, Integer type) throws BizException {
        OrganizationShopMicropageEntity entity = getMicropageInfo(null);
        if (type == 0) {//仅保存
            entity.setGuideRc(JsonUtil.toNormalJson(dto));
        } else if (type == 1) {//保存并发布
            entity.setGuideRc(JsonUtil.toNormalJson(dto));
            entity.setGuideRelease(JsonUtil.toNormalJson(dto));
        }
        organizationShopMicropageRepository.modifyAndFlush(entity);
    }

    @Override
    public void contactSaveOrPublish(OrganizationShopMicropageContactDTO dto, Integer type) throws BizException {
        OrganizationShopMicropageEntity entity = getMicropageInfo(null);
        if (type == 0) {//仅保存
            entity.setContactRc(JsonUtil.toNormalJson(dto));
        } else if (type == 1) {//保存并发布
            entity.setContactRc(JsonUtil.toNormalJson(dto));
            entity.setContactRelease(JsonUtil.toNormalJson(dto));
        }
        organizationShopMicropageRepository.modifyAndFlush(entity);
    }

    private OrganizationShopMicropageEntity getMicropageInfo(String sn) {
        //获取店铺SN
        String shopSn = (null == sn ? userCacheData.getCurrentUser().getUser().getWycShopSn() : sn);
        OrganizationShopMicropageEntity entity = organizationShopMicropageRepository.findByShopSn(shopSn);
        return entity;
    }
}
