package com.huboot.user.weixin.repository;

import com.huboot.commons.jpa.IBaseRepository;
import com.huboot.share.user_service.enums.MiniappReleaseStatusEnum;
import com.huboot.user.weixin.entity.MiniappReleaseLogEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*小程序发布记录Repository
*/
@Repository("mimiappReleaseLogRepository")
public interface IMiniappReleaseLogRepository extends IBaseRepository<MiniappReleaseLogEntity> {

    MiniappReleaseLogEntity findByMiniappIdAndAuditId(String miniappId, String auditId);

    List<MiniappReleaseLogEntity> findByTemplateIdAndStatus(String templateId, MiniappReleaseStatusEnum status);

    MiniappReleaseLogEntity findByAuditId(String auditId);

    List<MiniappReleaseLogEntity> findByMiniappIdAndStatus(String miniappId, MiniappReleaseStatusEnum status);
}
