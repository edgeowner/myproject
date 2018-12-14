package com.huboot.business.base_model.ali_service.service;

import com.huboot.business.base_model.ali_service.dto.template.AddTemplateReqDTO;
import com.huboot.business.base_model.ali_service.dto.template.DisableTemplateReqDTO;
import com.huboot.business.base_model.ali_service.entity.SystemSMSTemplateEntity;
import com.huboot.business.base_model.ali_service.repository.SystemSMSTemplateRepository;
import com.huboot.business.common.component.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MsgTemplateService {

    private final Logger logger = LoggerFactory.getLogger(MsgTemplateService.class);

    @Autowired
    private SystemSMSTemplateRepository systemSMSTemplateRepository;


    /***
     * 添加模板
     * **/
    public SystemSMSTemplateEntity add(AddTemplateReqDTO addTemplateReqDTO){
        if(systemSMSTemplateRepository.findOneByShopUidAndNodeAndStatus(addTemplateReqDTO.getShopUid(),addTemplateReqDTO.getNode(), SystemSMSTemplateEntity.SystemSMSTemplateEnum.enable) != null) throw new BizException("该节点模板已存在");
        SystemSMSTemplateEntity systemSMSTemplateEntity = new SystemSMSTemplateEntity();
        BeanUtils.copyProperties(addTemplateReqDTO,systemSMSTemplateEntity);
        systemSMSTemplateEntity.setStatus(SystemSMSTemplateEntity.SystemSMSTemplateEnum.enable);
        systemSMSTemplateEntity.setTemplateId(UUID.randomUUID().toString().replace("-",""));
        systemSMSTemplateEntity.setCreateTime(LocalDateTime.now());
        return systemSMSTemplateRepository.create(systemSMSTemplateEntity);
    }

    /***
     * 禁用模板
     * **/
    public void disable(DisableTemplateReqDTO disableTemplateReqDTO){
        SystemSMSTemplateEntity systemSMSTemplateEntity = systemSMSTemplateRepository.findOneByShopUidAndTemplateIdAndStatus(disableTemplateReqDTO.getShopUid(),disableTemplateReqDTO.getTemplateId(), SystemSMSTemplateEntity.SystemSMSTemplateEnum.enable);
        if(systemSMSTemplateEntity == null ) throw new BizException("模板不存在");
        systemSMSTemplateEntity.setStatus(SystemSMSTemplateEntity.SystemSMSTemplateEnum.disable);
        systemSMSTemplateEntity.setUpdateTime(LocalDateTime.now());
        systemSMSTemplateRepository.update(systemSMSTemplateEntity);
    }


}
