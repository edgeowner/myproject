package com.huboot.business.base_model.weixin_service.repository;

import com.huboot.business.base_model.weixin_service.entity.WeixinMimiappCodeTemplateEntity;
import com.huboot.business.common.jpa.IBaseRepository;
import org.springframework.stereotype.Repository;

/**
*小程序代码模板信息表Repository
*/
@Repository("weixinMimiappCodeTemplateRepository")
public interface IWeixinMimiappCodeTemplateRepository extends IBaseRepository<WeixinMimiappCodeTemplateEntity> {

    WeixinMimiappCodeTemplateEntity findByTemplateId(Integer lastTemplateId);

}
