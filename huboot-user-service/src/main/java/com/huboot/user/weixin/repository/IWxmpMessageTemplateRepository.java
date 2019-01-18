package com.huboot.user.weixin.repository;

import com.huboot.commons.jpa.IBaseRepository;
import com.huboot.share.user_service.enums.WeixinMessageNodeEnum;
import com.huboot.user.weixin.entity.WxmpMessageTemplateEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*公众号Repository
*/
@Repository("wxmpMessageTemplateRepository")
public interface IWxmpMessageTemplateRepository extends IBaseRepository<WxmpMessageTemplateEntity> {

    WxmpMessageTemplateEntity findByNode(WeixinMessageNodeEnum node);

    List<WxmpMessageTemplateEntity> findByTemplateIdShort(String templateIdShort);
}
