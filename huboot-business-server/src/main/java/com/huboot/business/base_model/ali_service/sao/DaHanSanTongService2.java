package com.huboot.business.base_model.ali_service.sao;

import com.dahantc.api.sms.json.JSONHttpClient;
import com.dahantc.api.sms.json.SmsData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huboot.business.base_model.ali_service.config.SMSConfigBean;
import com.huboot.business.base_model.ali_service.dto.PromotionBatchWithdTemplateReqDTO;
import com.huboot.business.base_model.ali_service.dto.batch.resp.BatchSendRespDTO;
import com.huboot.business.base_model.ali_service.repository.ISystemSMSLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

@Service
public class DaHanSanTongService2 {

    private final Logger logger = LoggerFactory.getLogger(DaHanSanTongService2.class);


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
     **/
    public void sendPromotionMsgBatch(PromotionBatchWithdTemplateReqDTO romotionBatchWithdTemplateReqDTO, String template) {
        FutureTask<BatchSendRespDTO> futureTask = new FutureTask<BatchSendRespDTO>(new PromotionMsgTask(romotionBatchWithdTemplateReqDTO, template));
        smsTaskExecutor.submit(futureTask);
    }


    class PromotionMsgTask implements Callable<BatchSendRespDTO> {

        private String template;

        PromotionBatchWithdTemplateReqDTO promotionBatchWithdTemplateReqDTO;

        public PromotionMsgTask() {

        }

        public PromotionMsgTask(PromotionBatchWithdTemplateReqDTO promotionBatchWithdTemplateReqDTO,String template) {
            this.promotionBatchWithdTemplateReqDTO = promotionBatchWithdTemplateReqDTO;
            this.template = template;
        }

        public PromotionBatchWithdTemplateReqDTO getPromotionBatchWithdTemplateReqDTO() {
            return promotionBatchWithdTemplateReqDTO;
        }

        public void setPromotionBatchWithdTemplateReqDTO(PromotionBatchWithdTemplateReqDTO promotionBatchWithdTemplateReqDTO) {
            this.promotionBatchWithdTemplateReqDTO = promotionBatchWithdTemplateReqDTO;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        private String buidlContent(String str, Map<String, String> mapstring) {
            if (StringUtils.isEmpty(str)) return str;
            if (mapstring == null || mapstring.keySet().size() == 0) return str;
            for (Map.Entry<String, String> entry : mapstring.entrySet()) {
                str = str.replace("{" + entry.getKey() + "}", entry.getValue());
            }
            return str;
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
            String content = buidlContent(template,promotionBatchWithdTemplateReqDTO.getParams());
            //construction conent msgid
            String reqId = UUID.randomUUID().toString().replace("-", "");
            //init json http client
            JSONHttpClient jsonHttpClient = new JSONHttpClient(smsConfigBean.getUrl());
            jsonHttpClient.setRetryCount(smsConfigBean.getRetryCount());
            //batch construction
            List<SmsData> list = new ArrayList<>();
            list.add(new SmsData(String.join(",", promotionBatchWithdTemplateReqDTO.getPhones()), content, reqId, smsConfigBean.getSign(), smsConfigBean.getSubCode(), null));
            //send
            String sendBatchRes = jsonHttpClient.sendBatchSms(smsConfigBean.getAccount2(), smsConfigBean.getPassword2(), list);
            logger.info("提交批量短信响应：" + sendBatchRes);
            BatchSendRespDTO respDTO = mapper.readValue(sendBatchRes, BatchSendRespDTO.class);
            if (respDTO.getResult().equals("0")) {
                promotionBatchWithdTemplateReqDTO.getPhones().forEach(s -> {
                    daHanSanTong.writeMsgLog(promotionBatchWithdTemplateReqDTO.getFromSys(),promotionBatchWithdTemplateReqDTO.getNode(),s,content,sendBatchRes,promotionBatchWithdTemplateReqDTO.getShopUid(),respDTO);
                });
            }
            return respDTO;
        }
    }
}
