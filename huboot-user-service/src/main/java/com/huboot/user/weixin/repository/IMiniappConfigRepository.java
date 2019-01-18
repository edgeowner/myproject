package com.huboot.user.weixin.repository;

import com.xiehua.commons.jpa.IBaseRepository;
import com.xiehua.share.user_service.enums.MiniappConfigTypeEnum;
import com.xiehua.user.weixin.entity.MiniappConfigEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*小程序配置Repository
*/
@Repository("mimiappConfigRepository")
public interface IMiniappConfigRepository extends IBaseRepository<MiniappConfigEntity> {

    MiniappConfigEntity findByMiniappIdAndType(String miniappId, MiniappConfigTypeEnum type);

    List<MiniappConfigEntity> findByMiniappId(String miniappId);
}
