package com.huboot.user.weixin.repository;

import com.huboot.commons.jpa.IBaseRepository;
import com.huboot.user.weixin.entity.WxmpEntity;
import org.springframework.stereotype.Repository;

/**
*小程序Repository
*/
@Repository("wxmpRepository")
public interface IWxmpRepository extends IBaseRepository<WxmpEntity> {

    WxmpEntity findByWxmpappId(String wxmpappId);

    WxmpEntity findByOriginalId(String originalId);
}
