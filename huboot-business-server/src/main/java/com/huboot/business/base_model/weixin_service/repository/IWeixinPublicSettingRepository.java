package com.huboot.business.base_model.weixin_service.repository;

import com.huboot.business.common.jpa.IBaseRepository;
import org.springframework.stereotype.Repository;
import com.huboot.business.base_model.weixin_service.entity.WeixinPublicSettingEntity;

import java.util.List;

/**
*商家微信公众号配置信息表Repository
*/
@Repository("weixinPublicSettingRepository")
public interface IWeixinPublicSettingRepository extends IBaseRepository<WeixinPublicSettingEntity> {

   List<WeixinPublicSettingEntity> findByWeixinUidAndSetTypeOrderByUpdateTimeDesc(String weixinUid,WeixinPublicSettingEntity.SetTypeEnum setType);
}
