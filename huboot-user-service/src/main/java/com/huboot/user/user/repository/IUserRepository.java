package com.huboot.user.user.repository;

import com.huboot.commons.jpa.IBaseRepository;
import com.huboot.share.user_service.enums.UserTypeEnum;
import com.huboot.user.user.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*用户服务-用户基础信息表Repository
*/
@Repository("userRepository")
public interface IUserRepository extends IBaseRepository<UserEntity> {

    UserEntity findByPhone(String phone);
    UserEntity findByUsername(String username);
    UserEntity findByName(String name);

    UserEntity findByUsernameOrPhone(String username, String phone);
    UserEntity findByPhoneAndOrganizationId(String phone,Long organizationId);
    List<UserEntity> findByOrganizationIdAndUserType(Long organizationId, UserTypeEnum userType);

}
