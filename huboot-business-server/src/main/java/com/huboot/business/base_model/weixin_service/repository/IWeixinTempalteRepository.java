package com.huboot.business.base_model.weixin_service.repository;

import com.huboot.business.base_model.weixin_service.entity.WeixinTempalteEntity;
import com.huboot.business.common.jpa.IBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*微信模板信息表Repository
*/
@Repository("weixinTempalteRepository")
public interface IWeixinTempalteRepository extends IBaseRepository<WeixinTempalteEntity> {

    List<WeixinTempalteEntity> findBySystem(Integer system);

    WeixinTempalteEntity findByNodeAndSystem(Integer node, Integer system);
}
