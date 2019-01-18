package com.huboot.user.role.repository;

import com.huboot.commons.jpa.IBaseRepository;
import org.springframework.stereotype.Repository;
import com.huboot.user.role.entity.RoleEntity;

import java.util.List;

/**
*用户服务-角色表Repository
*/
@Repository("roleRepository")
public interface IRoleRepository extends IBaseRepository<RoleEntity> {

    List<RoleEntity> findByOrganizationId(Long organizationId);

}
