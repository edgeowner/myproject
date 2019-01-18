package com.huboot.user.weixin.repository;

import com.xiehua.commons.jpa.IBaseRepository;
import com.xiehua.user.weixin.entity.WxmpUserSubscribeLogEntity;
import org.springframework.stereotype.Repository;

/**
*微信用户关注历史信息Repository
*/
@Repository("wxmpUserSubscribeLogRepository")
public interface IWxmpUserSubscribeLogRepository extends IBaseRepository<WxmpUserSubscribeLogEntity> {

}
