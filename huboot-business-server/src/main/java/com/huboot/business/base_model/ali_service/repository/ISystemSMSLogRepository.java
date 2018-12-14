package com.huboot.business.base_model.ali_service.repository;


import com.huboot.business.base_model.ali_service.entity.SystemSMSLogEntity;
import com.huboot.business.common.jpa.IBaseRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
*交易中心-Repository
*/
@Repository("systemSMSLogRepository")
public interface ISystemSMSLogRepository extends IBaseRepository<SystemSMSLogEntity> {

    Integer countByShopUidAndCreateTimeBetween(String shopUid, LocalDateTime startTime, LocalDateTime endTime);

    Integer countByShopUidAndStatusAndAndCreateTimeBetween(String shopUid, SystemSMSLogEntity.SystemSMSLogStatusEnum status, LocalDateTime startTime, LocalDateTime endTime);

}
