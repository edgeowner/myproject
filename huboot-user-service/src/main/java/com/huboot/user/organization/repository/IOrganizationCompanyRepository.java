package com.huboot.user.organization.repository;

import com.huboot.commons.jpa.IBaseRepository;
import org.springframework.stereotype.Repository;
import com.huboot.user.organization.entity.OrganizationCompanyEntity;

import java.util.List;

/**
*用户服务-公司表Repository
*/
@Repository("organizationCompanyRepository")
public interface IOrganizationCompanyRepository extends IBaseRepository<OrganizationCompanyEntity> {

    long countByCode(String code);
    long countByName(String name);
    List<OrganizationCompanyEntity> findByName(String name);
    OrganizationCompanyEntity findByOrganizationId(Long organizationId);

}
