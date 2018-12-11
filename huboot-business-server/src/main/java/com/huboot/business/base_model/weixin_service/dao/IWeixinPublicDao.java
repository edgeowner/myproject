package com.huboot.business.base_model.weixin_service.dao;

import com.huboot.business.base_model.weixin_service.entity.WeixinPublicEntity;
import org.springframework.data.domain.Page;

/**
*商家微信公众号配置信息表Dao
*/
public interface IWeixinPublicDao {

    Page<WeixinPublicEntity> getPublicPage(String shopUid, String publicUid, Integer page, Integer size);

    Page<WeixinPublicEntity> getMiniappPage(String shopUid, String publicUid, Integer page, Integer size);

}
