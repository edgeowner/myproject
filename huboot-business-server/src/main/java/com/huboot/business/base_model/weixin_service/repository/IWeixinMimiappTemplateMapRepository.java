package com.huboot.business.base_model.weixin_service.repository;

import com.huboot.business.common.jpa.IBaseRepository;
import org.springframework.stereotype.Repository;
import com.huboot.business.base_model.weixin_service.entity.WeixinMimiappTemplateMapEntity;

import java.util.List;

/**
*小程序模板使用关系表Repository
*/
@Repository("weixinMimiappTemplateMapRepository")
public interface IWeixinMimiappTemplateMapRepository extends IBaseRepository<WeixinMimiappTemplateMapEntity> {

    List<WeixinMimiappTemplateMapEntity> findByWeixinUidAndStatusOrderByUpdateTimeDesc(String weixinUid,WeixinMimiappTemplateMapEntity.StatusEnum status);

    List<WeixinMimiappTemplateMapEntity> findByWeixinUidAndStatusInOrderByUpdateTimeDesc(String weixinUid,List<WeixinMimiappTemplateMapEntity.StatusEnum> statusList);

    List<WeixinMimiappTemplateMapEntity> findByCodeTempalteIdAndStatus(Integer codeTempalteId, WeixinMimiappTemplateMapEntity.StatusEnum status);
}
