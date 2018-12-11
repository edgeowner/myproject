package com.huboot.business.base_model.weixin_service.repository;

import com.huboot.business.base_model.weixin_service.entity.WeixinUserPublicMapEntity;
import com.huboot.business.common.jpa.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*微信用户关注公众号关系表Repository
*/
@Repository("weixinUserPublicMapRepository")
public interface IWeixinUserPublicMapRepository extends IBaseRepository<WeixinUserPublicMapEntity> {

    WeixinUserPublicMapEntity findByOpenIdAndWeixinUid(String openId, String weixinUid);

    WeixinUserPublicMapEntity findByOpenId(String openId);

    List<WeixinUserPublicMapEntity> findByWeixinUid(String weixinUid);

    @Query(value = "select count(1) from xiehua_weixin_user_public_map m, xiehua_weixin_user u " +
            "where m.open_id = u.open_id and m.weixin_uid = :weixinUid " +
            "and u.subscribe = 1 and u.subscribe_source = :promotionSource ", nativeQuery = true)
    Integer promotionSourceCount(@Param("weixinUid") String weixinUid, @Param("promotionSource")String promotionSource);
}
