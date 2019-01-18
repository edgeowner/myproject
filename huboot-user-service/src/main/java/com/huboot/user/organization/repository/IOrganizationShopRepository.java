package com.huboot.user.organization.repository;

import com.huboot.commons.jpa.IBaseRepository;
import com.huboot.user.organization.entity.OrganizationShopEntity;
import org.springframework.stereotype.Repository;

/**
*用户服务-部门店铺表Repository
*/
@Repository("organizationShopRepository")
public interface IOrganizationShopRepository extends IBaseRepository<OrganizationShopEntity> {
    OrganizationShopEntity findBySn(String sn);
    OrganizationShopEntity findByOrganizationId(Long organizationId);
}
