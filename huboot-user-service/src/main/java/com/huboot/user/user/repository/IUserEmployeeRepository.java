package com.huboot.user.user.repository;

import com.huboot.commons.jpa.IBaseRepository;
import org.springframework.stereotype.Repository;
import com.huboot.user.user.entity.UserEmployeeEntity;

/**
 * 用户服务-企业信息表Repository
 */
@Repository("userEmployeeRepository")
public interface IUserEmployeeRepository extends IBaseRepository<UserEmployeeEntity> {

    UserEmployeeEntity findByUserId(Long userId);

}
