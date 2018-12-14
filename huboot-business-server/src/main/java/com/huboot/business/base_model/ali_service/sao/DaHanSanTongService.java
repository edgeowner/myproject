package com.huboot.business.base_model.ali_service.sao;

import com.dahantc.api.sms.json.JSONHttpClient;
import com.dahantc.api.sms.json.SmsData;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.huboot.business.base_model.ali_service.config.SMSConfigBean;
import com.huboot.business.base_model.ali_service.dto.PromotionBatchReqDTO;
import com.huboot.business.base_model.ali_service.dto.batch.resp.BatchSendRespDTO;
import com.huboot.business.base_model.ali_service.repository.ISystemSMSLogRepository;
import com.huboot.business.common.enums.SMSNodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

@Service
public class DaHanSanTongService {

    private final Logger logger = LoggerFactory.getLogger(DaHanSanTongService.class);


    @Autowired
    private SMSConfigBean smsConfigBean;

    @Autowired
    private ThreadPoolTaskExecutor smsTaskExecutor;

    @Autowired
    private ISystemSMSLogRepository systemSMSLogRepository;

    @Autowired
    @Qualifier("objectMapper")
    private ObjectMapper mapper;

    @Autowired
    private DaHanSanTong daHanSanTong;



    /**
     * 批量发送营销短信
     * **/
    public void sendPromotionMsgBatch(PromotionBatchReqDTO promotionBatchReqDTO){
//        FutureTask<BatchSendRespDTO> futureTask = new FutureTask<BatchSendRespDTO>(new PromotionMsgTask(promotionBatchReqDTO)){
//            @Override
//            protected void done() {
//                try {
//                    BatchSendRespDTO response = this.get();
//                    String resp = mapper.writeValueAsString(response);
//                    if(response.getResult().equals("0")){
//                        promotionBatchReqDTO.getPhones().forEach(s ->{
//                            SystemSMSLogEntity logEntity = new SystemSMSLogEntity();
//                            logEntity.setSystem(promotionBatchReqDTO.getFromSys());
//                            logEntity.setNode(promotionBatchReqDTO.getNode());
//                            logEntity.setPhone(s);
//                            logEntity.setSmsRequest(response.getReq());
//                            logEntity.setSmsResponse(resp);
//                            logEntity.setStatus(SystemSMSLogEntity.SystemSMSLogStatusEnum.success);
//                            if(response.getData().get(0).getFailPhones().contains(s)) logEntity.setStatus(SystemSMSLogEntity.SystemSMSLogStatusEnum.fail);
//                            systemSMSLogRepository.create(logEntity);
//                        });
//                        return ;
//                    }
//                    throw new BizException("营销短信发送失败:"+ resp);
//
//                }catch (InterruptedException | ExecutionException | JsonProcessingException e){
//                    logger.error("营销短信发送失败:",e);
//                }
//            }
//        };
        FutureTask<BatchSendRespDTO> futureTask = new FutureTask<BatchSendRespDTO>(new PromotionMsgTask(promotionBatchReqDTO));
        smsTaskExecutor.submit(futureTask);
       // futureTask.get()
        //CompletionService<BatchSendRespDTO> completionService = new ExecutorCompletionService<BatchSendRespDTO>(smsTaskExecutor);
    }


     class PromotionMsgTask implements Callable<BatchSendRespDTO> {

         PromotionBatchReqDTO promotionBatchReqDTO;

         public PromotionMsgTask() {

         }

         public PromotionMsgTask(PromotionBatchReqDTO promotionBatchReqDTO) {
             this.promotionBatchReqDTO = promotionBatchReqDTO;
         }

         public PromotionBatchReqDTO getPromotionBatchReqDTO() {
             return promotionBatchReqDTO;
         }

         public void setPromotionBatchReqDTO(PromotionBatchReqDTO promotionBatchReqDTO) {
             this.promotionBatchReqDTO = promotionBatchReqDTO;
         }

         /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return computed result
         * @throws Exception if unable to compute a result
         */
         @Override
         public BatchSendRespDTO call() throws Exception {
             //construction conent
             List<String> params = promotionBatchReqDTO.getParams();
             String content = MessageFormat.format(SMSNodeEnum.valueOf(promotionBatchReqDTO.getNode()).getContent(), params.toArray(new String[params.size()]));
             //construction conent msgid
             String reqId = UUID.randomUUID().toString().replace("-", "");
             //init json http client
             JSONHttpClient jsonHttpClient = new JSONHttpClient(smsConfigBean.getUrl());
             jsonHttpClient.setRetryCount(smsConfigBean.getRetryCount());
             //batch construction
             List<SmsData> list = new ArrayList<>();
             list.add(new SmsData(String.join(",", promotionBatchReqDTO.getPhones()), content, reqId, smsConfigBean.getSign(), smsConfigBean.getSubCode(), null));
             //send
             String sendBatchRes = jsonHttpClient.sendBatchSms(smsConfigBean.getAccount2(), smsConfigBean.getPassword2(), list);
             logger.info("提交批量短信响应：" + sendBatchRes);
             BatchSendRespDTO respDTO = mapper.readValue(sendBatchRes, BatchSendRespDTO.class);
             if(respDTO.getResult().equals("0")) {
                 promotionBatchReqDTO.getPhones().forEach(s -> {
                     daHanSanTong.writeMsgLog(promotionBatchReqDTO.getFromSys(),promotionBatchReqDTO.getNode(),s,content,sendBatchRes,promotionBatchReqDTO.getShopUid(),respDTO);
                 });
             }
             return respDTO;
         }
     }
}
