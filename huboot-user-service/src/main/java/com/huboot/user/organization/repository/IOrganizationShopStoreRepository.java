package com.huboot.user.organization.repository;

import com.huboot.commons.jpa.IBaseRepository;
import com.huboot.share.common.enums.YesOrNoEnum;
import com.huboot.user.organization.entity.OrganizationShopStoreEntity;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 组织-店铺门店表Repository
 */
@Repository("organizationShopStoreRepository")
public interface IOrganizationShopStoreRepository extends IBaseRepository<OrganizationShopStoreEntity> {

    long countByShopId(Long shopId);

    List<OrganizationShopStoreEntity> findByShopIdAndDefaultStatus(Long shopId, YesOrNoEnum defaultStatus);
}
