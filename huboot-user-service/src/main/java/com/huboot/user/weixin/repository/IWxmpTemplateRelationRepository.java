package com.huboot.user.weixin.repository;

import com.huboot.commons.jpa.IBaseRepository;
import com.huboot.user.weixin.entity.WxmpTemplateRelationEntity;
import org.springframework.stereotype.Repository;

/**
*公众号Repository
*/
@Repository("wxmpTemplateRelationRepository")
public interface IWxmpTemplateRelationRepository extends IBaseRepository<WxmpTemplateRelationEntity> {

    WxmpTemplateRelationEntity findByWxmpappIdAndRelaTemplateId(String wxmpappId, Long relaTemplateId);
}
