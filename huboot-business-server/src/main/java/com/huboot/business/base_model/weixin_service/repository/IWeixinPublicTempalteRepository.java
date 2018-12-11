package com.huboot.business.base_model.weixin_service.repository;

import com.huboot.business.base_model.weixin_service.entity.WeixinPublicTempalteEntity;
import com.huboot.business.common.jpa.IBaseRepository;
import org.springframework.stereotype.Repository;

/**
*微信模板信息表Repository
*/
@Repository("weixinPublicTempalteRepository")
public interface IWeixinPublicTempalteRepository extends IBaseRepository<WeixinPublicTempalteEntity> {

    WeixinPublicTempalteEntity findByWeixinUidAndRelaTemplateId(String weixinUid, Integer relaTemplateId);
}
