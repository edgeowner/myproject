package com.huboot.user.user.repository;

import com.huboot.commons.jpa.IBaseRepository;
import com.huboot.user.user.entity.UserRoleRelationEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户服务-用户角色关系表Repository
 */
@Repository("userRoleRelationRepository")
public interface IUserRoleRelationRepository extends IBaseRepository<UserRoleRelationEntity> {

    List<UserRoleRelationEntity> findByUserId(Long userId);
    List<UserRoleRelationEntity> findByRoleId(Long roleId);

    UserRoleRelationEntity findByUserIdAndRoleId(Long userId, Long roleId);
}
