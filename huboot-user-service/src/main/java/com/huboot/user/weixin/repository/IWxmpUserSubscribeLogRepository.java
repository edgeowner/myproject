package com.huboot.user.weixin.repository;

import com.huboot.commons.jpa.IBaseRepository;
import com.huboot.user.weixin.entity.WxmpUserSubscribeLogEntity;
import org.springframework.stereotype.Repository;

/**
*微信用户关注历史信息Repository
*/
@Repository("wxmpUserSubscribeLogRepository")
public interface IWxmpUserSubscribeLogRepository extends IBaseRepository<WxmpUserSubscribeLogEntity> {

}
