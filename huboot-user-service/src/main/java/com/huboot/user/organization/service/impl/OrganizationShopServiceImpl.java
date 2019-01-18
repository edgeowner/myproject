package com.huboot.user.organization.service.impl;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.jpa.QueryCondition;
import com.huboot.commons.page.ShowPageImpl;
import com.huboot.share.user_service.api.dto.ShopDetaiInfo;
import com.huboot.user.organization.entity.OrganizationShopEntity;
import com.huboot.user.organization.repository.IOrganizationShopRepository;
import com.huboot.user.organization.service.IOrganizationShopService;
import com.huboot.user.organization.dto.OrganizationShopCreateReqDTO;
import com.huboot.user.organization.dto.OrganizationShopModifyReqDTO;
import com.huboot.user.organization.dto.OrganizationShopQueryReqDTO;
import com.huboot.user.organization.dto.OrganizationShopDetailResDTO;
import com.huboot.user.organization.dto.OrganizationShopPageResDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户服务-部门店铺表ServiceImpl
 */
@Service("organizationShopServiceImpl")
public class OrganizationShopServiceImpl implements IOrganizationShopService {

    private Logger logger = LoggerFactory.getLogger(OrganizationShopServiceImpl.class);

    @Autowired
    private IOrganizationShopRepository organizationShopRepository;

    @Override
    public ShopDetaiInfo findDetail(Long shopId) {
        OrganizationShopEntity entity = organizationShopRepository.find(shopId);
        ShopDetaiInfo info = new ShopDetaiInfo();
        BeanUtils.copyProperties(entity, info);
        return info;
    }

    @Override
    public ShopDetaiInfo findByShopSn(String shopSn) throws BizException {
        OrganizationShopEntity organizationShopEntity = organizationShopRepository.findBySn(shopSn);
        ShopDetaiInfo info = new ShopDetaiInfo();
        if (null == organizationShopEntity) {
            throw new BizException("未查询到组织店铺");
        }
        BeanUtils.copyProperties(organizationShopEntity, info);
        return info;
    }

    @Transactional
    @Override
    public void create(OrganizationShopCreateReqDTO createReqDTO) throws BizException {
        OrganizationShopEntity entity = new OrganizationShopEntity();
        BeanUtils.copyProperties(createReqDTO, entity);
        organizationShopRepository.create(entity);
    }

    @Transactional
    @Override
    public void modify(OrganizationShopModifyReqDTO modifyReqDTO) throws BizException {
        OrganizationShopEntity entity = organizationShopRepository.find(modifyReqDTO.getId());
        BeanUtils.copyProperties(modifyReqDTO, entity);
        organizationShopRepository.modify(entity);
    }

    @Transactional
    @Override
    public void delete(Long id) throws BizException {
        organizationShopRepository.remove(id);
    }

    @Override
    public OrganizationShopDetailResDTO find(Long id) throws BizException {
        OrganizationShopEntity entity = organizationShopRepository.find(id);
        OrganizationShopDetailResDTO dto = new OrganizationShopDetailResDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public ShowPageImpl<OrganizationShopPageResDTO> findPage(OrganizationShopQueryReqDTO queryReqDTO) throws BizException {

        Page<OrganizationShopEntity> page = organizationShopRepository.findPage(QueryCondition.from(OrganizationShopEntity.class).where(list -> {

        }).sort(Sort.by("id").descending()).limit(queryReqDTO.getPage(), queryReqDTO.getSize()));

        return new ShowPageImpl<>(page).map(entity -> {
            OrganizationShopPageResDTO dto = new OrganizationShopPageResDTO();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        });
    }

}
