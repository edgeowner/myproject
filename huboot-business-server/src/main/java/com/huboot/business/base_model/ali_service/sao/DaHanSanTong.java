package com.huboot.business.base_model.ali_service.sao;


import com.huboot.business.base_model.ali_service.dto.batch.resp.BatchSendRespDTO;
import com.huboot.business.base_model.ali_service.entity.SystemSMSLogEntity;
import com.huboot.business.base_model.ali_service.repository.ISystemSMSLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DaHanSanTong {

    private final Logger logger = LoggerFactory.getLogger(DaHanSanTong.class);

    @Autowired
    private ISystemSMSLogRepository systemSMSLogRepository;


    protected void writeMsgLog(Integer fromSys, Integer node, String phone, String req, String resp, String shopUid, BatchSendRespDTO respDTO){
        SystemSMSLogEntity logEntity = new SystemSMSLogEntity();
        logEntity.setSystem(fromSys);
        logEntity.setNode(node);
        logEntity.setPhone(phone);
        logEntity.setSmsRequest(req);
        logEntity.setSmsResponse(resp);
        logEntity.setShopUid(shopUid);
        logEntity.setStatus(SystemSMSLogEntity.SystemSMSLogStatusEnum.success);
        if(respDTO.getData() != null && respDTO.getData().size() > 0 ){
            if (respDTO.getData().get(0).getFailPhones().contains(phone)) logEntity.setStatus(SystemSMSLogEntity.SystemSMSLogStatusEnum.fail);
        }
        systemSMSLogRepository.create(logEntity);
    }

}
