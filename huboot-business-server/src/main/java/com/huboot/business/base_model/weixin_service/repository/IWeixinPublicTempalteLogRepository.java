package com.huboot.business.base_model.weixin_service.repository;

import com.huboot.business.base_model.weixin_service.entity.WeixinPublicTempalteLogEntity;
import com.huboot.business.common.jpa.IBaseRepository;
import org.springframework.stereotype.Repository;

/**
*微信公众号发送模板消息日志表Repository
*/
@Repository("weixinPublicTempalteLogRepository")
public interface IWeixinPublicTempalteLogRepository extends IBaseRepository<WeixinPublicTempalteLogEntity> {

}
