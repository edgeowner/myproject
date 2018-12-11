package com.huboot.business.base_model.weixin_service.repository;

import com.huboot.business.common.jpa.IBaseRepository;
import org.springframework.stereotype.Repository;
import com.huboot.business.base_model.weixin_service.entity.WeixinUserEntity;

/**
*微信用户表Repository
*/
@Repository("weixinUserRepository")
public interface IWeixinUserRepository extends IBaseRepository<WeixinUserEntity> {

    WeixinUserEntity findByOpenId(String openId);
}
