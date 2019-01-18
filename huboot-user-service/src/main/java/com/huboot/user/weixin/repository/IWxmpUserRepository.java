package com.huboot.user.weixin.repository;

import com.huboot.commons.jpa.IBaseRepository;
import com.huboot.user.weixin.entity.WxmpUserEntity;
import org.springframework.stereotype.Repository;

/**
*小程序微信用户Repository
*/
@Repository("wxmpUserRepository")
public interface IWxmpUserRepository extends IBaseRepository<WxmpUserEntity> {

    WxmpUserEntity findByOpenId(String openId);

    WxmpUserEntity findByUnionId(String unionId);
}
