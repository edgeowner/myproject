package com.huboot.business.base_model.ali_service.service;



import com.huboot.business.base_model.ali_service.dto.PromotionBatchReqDTO;
import com.huboot.business.base_model.ali_service.dto.PromotionBatchWithdTemplateReqDTO;
import com.huboot.business.base_model.ali_service.dto.SmsPromotionCountRespDTO;
import com.huboot.business.base_model.ali_service.entity.SystemSMSLogEntity;
import com.huboot.business.base_model.ali_service.entity.SystemSMSTemplateEntity;
import com.huboot.business.base_model.ali_service.repository.ISystemSMSLogRepository;
import com.huboot.business.base_model.ali_service.repository.SystemSMSTemplateRepository;
import com.huboot.business.base_model.ali_service.sao.DaHanSanTongService;
import com.huboot.business.base_model.ali_service.sao.DaHanSanTongService2;
import com.huboot.business.common.component.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 营销短信接口
 * **/
@Service
public class MsgPromotionService {

    private final Logger logger = LoggerFactory.getLogger(MsgPromotionService.class);

    @Autowired
    private DaHanSanTongService daHanSanTongService;

    @Autowired
    private DaHanSanTongService2 daHanSanTongService2;

    @Autowired
    private ISystemSMSLogRepository systemSMSLogRepository;

    @Autowired
    private SystemSMSTemplateRepository systemSMSTemplateRepository;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    /**
     * 批量发送营销短信
     * **/
    public void sendPromotionMsgBatch(PromotionBatchReqDTO promotionBatchReqDTO){
        daHanSanTongService.sendPromotionMsgBatch(promotionBatchReqDTO);
    }

    /**
     * 批量发送营销短信(使用模板)
     * **/
    public void sendPromotionMsgBatchWithTemplate(PromotionBatchWithdTemplateReqDTO romotionBatchWithdTemplateReqDTO){
        SystemSMSTemplateEntity templateEntity = systemSMSTemplateRepository.findOneByShopUidAndTemplateIdAndStatus(romotionBatchWithdTemplateReqDTO.getShopUid(),romotionBatchWithdTemplateReqDTO.getTemplateId(), SystemSMSTemplateEntity.SystemSMSTemplateEnum.enable);
        if(templateEntity == null) throw new BizException("模板不存在或已禁用");
        daHanSanTongService2.sendPromotionMsgBatch(romotionBatchWithdTemplateReqDTO,templateEntity.getTemplate());
    }

    /**
     * 发送营销短信统计
     *
     * @param shopUid
     * @param startTime
     * @param endTime
     **/
    public SmsPromotionCountRespDTO countPromotionMsg(String shopUid, String startTime, String endTime){
        SmsPromotionCountRespDTO promotionCountRespDTO = new SmsPromotionCountRespDTO();
        promotionCountRespDTO.setTotalSend(systemSMSLogRepository.countByShopUidAndCreateTimeBetween(shopUid,LocalDateTime.parse(startTime,formatter),LocalDateTime.parse(endTime,formatter)));
        promotionCountRespDTO.setTotalSendSuccessed(systemSMSLogRepository.countByShopUidAndStatusAndAndCreateTimeBetween(shopUid, SystemSMSLogEntity.SystemSMSLogStatusEnum.success,LocalDateTime.parse(startTime,formatter),LocalDateTime.parse(endTime,formatter)));
        return promotionCountRespDTO;
    }
}
