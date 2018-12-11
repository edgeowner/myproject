package com.huboot.business.base_model.weixin_service.repository;

import com.huboot.business.common.jpa.IBaseRepository;
import org.springframework.stereotype.Repository;
import com.huboot.business.base_model.weixin_service.entity.WeixinPublicMenuEntity;

import java.util.List;

/**
*微信公众号菜单Repository
*/
@Repository("weixinPublicMenuRepository")
public interface IWeixinPublicMenuRepository extends IBaseRepository<WeixinPublicMenuEntity> {

    List<WeixinPublicMenuEntity> findByWeixinUid(String weixinUid);

}
