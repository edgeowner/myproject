package com.huboot.user.weixin.repository;

import com.huboot.commons.jpa.IBaseRepository;
import com.huboot.user.weixin.entity.WxmpMessageLogEntity;
import org.springframework.stereotype.Repository;

/**
*公众号Repository
*/
@Repository("wxmpMessageLogRepository")
public interface IWxmpMessageLogRepository extends IBaseRepository<WxmpMessageLogEntity> {

}
