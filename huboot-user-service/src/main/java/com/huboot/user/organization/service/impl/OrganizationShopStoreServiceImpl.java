package com.huboot.user.organization.service.impl;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.jpa.QueryCondition;
import com.huboot.commons.page.ShowPageImpl;
import com.huboot.share.common.enums.YesOrNoEnum;
import com.huboot.share.user_service.data.AreaCacheData;
import com.huboot.share.user_service.data.UserCacheData;
import com.huboot.user.organization.dto.zkshop.*;
import com.huboot.user.organization.entity.OrganizationShopStoreEntity;
import com.huboot.user.organization.repository.IOrganizationShopStoreRepository;
import com.huboot.user.organization.service.IOrganizationShopStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 组织-店铺门店表ServiceImpl
 */
@Slf4j
@Service("organizationShopStoreServiceImpl")
public class OrganizationShopStoreServiceImpl implements IOrganizationShopStoreService {

    @Autowired
    private IOrganizationShopStoreRepository organizationShopStoreRepository;
    @Autowired
    private UserCacheData userCacheData;
    @Autowired
    private AreaCacheData areaCacheData;

    @Transactional
    @Override
    public void create(ZkshopOrganizationShopStoreCreateReqDTO createReqDTO) throws BizException {
        Long zkShopId = userCacheData.getCurrentUserEmployeeZkShopId();
        OrganizationShopStoreEntity entity = new OrganizationShopStoreEntity();
        BeanUtils.copyProperties(createReqDTO, entity);
        entity.setShopId(zkShopId);
        entity.setDefaultStatus(YesOrNoEnum.no);
        organizationShopStoreRepository.create(entity);
    }

    @Transactional
    @Override
    public void modify(ZkshopOrganizationShopStoreModifyReqDTO modifyReqDTO) throws BizException {
        OrganizationShopStoreEntity entity = organizationShopStoreRepository.find(modifyReqDTO.getId());
        BeanUtils.copyProperties(modifyReqDTO, entity);
        organizationShopStoreRepository.modify(entity);
    }

    @Override
    @Transactional
    public void modifyDefaultStatus(Long id) throws BizException {
        //找到已经
        OrganizationShopStoreEntity entity = organizationShopStoreRepository.find(id);
        List<OrganizationShopStoreEntity> organizationShopStoreEntityList = organizationShopStoreRepository.findByShopIdAndDefaultStatus(entity.getShopId(), YesOrNoEnum.yes);
        if (!CollectionUtils.isEmpty(organizationShopStoreEntityList)) {
            organizationShopStoreEntityList.forEach(organizationShopStoreEntity -> {
                organizationShopStoreEntity.setDefaultStatus(YesOrNoEnum.no);
            });
            organizationShopStoreRepository.modify(organizationShopStoreEntityList);
        }
        entity.setDefaultStatus(YesOrNoEnum.yes);
        organizationShopStoreRepository.modify(entity);
    }

    @Transactional
    @Override
    public void delete(Long id) throws BizException {
        organizationShopStoreRepository.remove(id);
    }

    @Override
    public ZkshopOrganizationShopStoreDetailResDTO find(Long id) throws BizException {
        OrganizationShopStoreEntity entity = organizationShopStoreRepository.find(id);
        ZkshopOrganizationShopStoreDetailResDTO dto = new ZkshopOrganizationShopStoreDetailResDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setTypeName(dto.getType().getShowName());
        dto.setFullArea(areaCacheData.getById(dto.getAreaId()).getFullName());
        return dto;
    }

    @Override
    public ShowPageImpl<ZkshopOrganizationShopStorePageResDTO> findPage(ZkshopOrganizationShopStoreQueryReqDTO queryReqDTO) throws BizException {

        Page<OrganizationShopStoreEntity> page = organizationShopStoreRepository.findPage(QueryCondition.from(OrganizationShopStoreEntity.class).where(list -> {

        }).sort(Sort.by("defaultStatus","id").descending()).limit(queryReqDTO.getPage(), queryReqDTO.getSize()));

        return new ShowPageImpl<>(page).map(entity -> {
            ZkshopOrganizationShopStorePageResDTO dto = new ZkshopOrganizationShopStorePageResDTO();
            BeanUtils.copyProperties(entity, dto);
            dto.setTypeName(dto.getType().getShowName());
            dto.setFullArea(areaCacheData.getById(dto.getAreaId()).getFullName());
            return dto;
        });
    }
}
