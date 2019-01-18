package com.huboot.user.weixin.repository;

import com.huboot.commons.jpa.IBaseRepository;
import com.huboot.user.weixin.entity.MiniappCodeTemplateEntity;
import org.springframework.stereotype.Repository;

/**
*小程序代码库Repository
*/
@Repository("miniappCodeTemplateRepository")
public interface IMiniappCodeTemplateRepository extends IBaseRepository<MiniappCodeTemplateEntity> {

    MiniappCodeTemplateEntity findByTemplateId(String templateId);
}
