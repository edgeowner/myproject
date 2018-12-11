package com.huboot.business.base_model.weixin_service.repository;

import com.huboot.business.base_model.weixin_service.entity.WeixinPublicEntity;
import com.huboot.business.common.jpa.IBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*商家微信公众号配置信息表Repository
*/
@Repository("weixinPublicRepository")
public interface IWeixinPublicRepository extends IBaseRepository<WeixinPublicEntity> {

    WeixinPublicEntity findByWeixinUid(String weixinUid);

    WeixinPublicEntity findByOriginalId(String originalId);

    WeixinPublicEntity findByAppId(String appId);

    List<WeixinPublicEntity> findByTypeAndBindType(WeixinPublicEntity.TypeEnum typeEnum, WeixinPublicEntity.BindTypeEnum bindTypeEnum);

    List<WeixinPublicEntity> findByTypeAndSystem(WeixinPublicEntity.TypeEnum typeEnum, Integer system);

}
