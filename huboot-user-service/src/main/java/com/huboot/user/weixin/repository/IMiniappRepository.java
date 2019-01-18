package com.huboot.user.weixin.repository;

import com.huboot.commons.jpa.IBaseRepository;
import com.huboot.share.common.enums.YesOrNoEnum;
import com.huboot.user.weixin.entity.MiniappEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*小程序Repository
*/
@Repository("mimiappRepository")
public interface IMiniappRepository extends IBaseRepository<MiniappEntity> {

    MiniappEntity findByMiniappId(String miniappId);

    List<MiniappEntity> findByCanBitchRelease(YesOrNoEnum canBitchRelease);
}
