package com.huboot.user.weixin.repository;

import com.huboot.commons.jpa.IBaseRepository;
import com.huboot.user.weixin.entity.WeixinShopRelationEntity;
import org.springframework.stereotype.Repository;

/**
*小程序Repository
*/
@Repository("weixinShopRelationRepository")
public interface IWeixinShopRelationRepository extends IBaseRepository<WeixinShopRelationEntity> {

    WeixinShopRelationEntity findByShopId(Long shopId);

    WeixinShopRelationEntity findByMiniappId(String miniappId);

    WeixinShopRelationEntity findByWxmpId(String wxmpId);
}
