package com.huboot.business.base_model.ali_service.repository;


import com.huboot.business.base_model.ali_service.entity.SystemSMSTemplateEntity;
import com.huboot.business.common.jpa.IBaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 交易中心-Repository
 */
@Repository
public interface SystemSMSTemplateRepository extends IBaseRepository<SystemSMSTemplateEntity> {

    SystemSMSTemplateEntity findOneByShopUidAndNodeAndStatus(String shopUid, Integer node, SystemSMSTemplateEntity.SystemSMSTemplateEnum status);


    SystemSMSTemplateEntity findOneByShopUidAndTemplateIdAndStatus(String shopUid, String templateId, SystemSMSTemplateEntity.SystemSMSTemplateEnum status);

}
