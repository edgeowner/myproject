package com.huboot.user.weixin.repository;

import com.huboot.commons.jpa.IBaseRepository;
import com.huboot.user.weixin.entity.MiniappUserEntity;
import org.springframework.stereotype.Repository;

/**
*小程序微信用户Repository
*/
@Repository("mimiappUserRepository")
public interface IMiniappUserRepository extends IBaseRepository<MiniappUserEntity> {

    MiniappUserEntity findByOpenId(String openId);
}
