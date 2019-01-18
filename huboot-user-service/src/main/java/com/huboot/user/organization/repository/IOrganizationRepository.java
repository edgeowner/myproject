package com.huboot.user.organization.repository;

import com.huboot.commons.jpa.IBaseRepository;
import com.huboot.user.organization.entity.OrganizationEntity;
import org.springframework.stereotype.Repository;

/**
*用户服务-部门基础表Repository
*/
@Repository("organizationRepository")
public interface IOrganizationRepository extends IBaseRepository<OrganizationEntity> {

    OrganizationEntity findByParentIdAndName(Long parentId,String name);

}
