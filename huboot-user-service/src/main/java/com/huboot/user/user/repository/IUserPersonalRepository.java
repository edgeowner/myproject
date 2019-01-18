package com.huboot.user.user.repository;

import com.huboot.commons.jpa.IBaseRepository;
import org.springframework.stereotype.Repository;
import com.huboot.user.user.entity.UserPersonalEntity;

/**
*用户服务-个人信息表Repository
*/
@Repository("userPersonalRepository")
public interface IUserPersonalRepository extends IBaseRepository<UserPersonalEntity> {

    UserPersonalEntity findByUserId(Long userId);
}
