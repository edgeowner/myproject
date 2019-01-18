package com.huboot.user.organization.repository;

import com.huboot.commons.jpa.IBaseRepository;
import org.springframework.stereotype.Repository;
import com.huboot.user.organization.entity.OrganizationShopMicropageEntity;

/**
*组织-微页面表Repository
*/
@Repository("organizationShopMicropageRepository")
public interface IOrganizationShopMicropageRepository extends IBaseRepository<OrganizationShopMicropageEntity> {

    /**
     * 根据店铺SN查询单个对象
     *
     * @param shopSn
     * @return
     */
    OrganizationShopMicropageEntity findByShopSn(String shopSn);
}
