package com.huboot.user.weixin.repository;

import com.huboot.commons.jpa.IBaseRepository;
import com.huboot.user.weixin.entity.WxmpMenuEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*微信公众号菜单Repository
*/
@Repository("wxmpMenuRepository")
public interface IWxmpMenuRepository extends IBaseRepository<WxmpMenuEntity> {
    List<WxmpMenuEntity> findByWxmpappId(String wxmpappId);
}
