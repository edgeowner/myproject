package com.huboot.account.notify;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huboot.account.payment.service.PayService;
import com.huboot.account.support.yibao.Robot;
import com.huboot.account.support.yibao.YiBaoService;
import com.huboot.share.account_service.api.dto.yibao.pay.YiBaoPayRespDTO;
import com.huboot.share.account_service.api.dto.yibao.refund.YiBaoRefundResp3DTO;
import com.huboot.share.account_service.api.dto.yibao.tradedivide.TradedivideResp2DTO;
import com.yeepay.g3.sdk.yop.encrypt.CertTypeEnum;
import com.yeepay.g3.sdk.yop.encrypt.DigitalEnvelopeDTO;
import com.yeepay.g3.sdk.yop.utils.DigitalEnvelopeUtils;
import com.yeepay.g3.sdk.yop.utils.InternalConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;

@Service
@Slf4j
public class NotifyService {

    public static String NOTIFY_TYPE_REG = "1";//商户入网回调

    public static String NOTIFY_TYPE_PAY = "2";//下单支付回调

    public static String NOTIFY_TYPE_REFUND = "3";//退款回调

    public static String NOTIFY_TYPE_DIVIDE = "4";// 分账回调

    public static String NOTIFY_TYPE_CASH_WITHDRAWA = "5";// 提现回调

    @Autowired
    private Robot robot;

    @Autowired
    private PayService payService;

    @Autowired
    private ObjectMapper mapper;

    /**
     * 创建易宝分账账户回调
     *
     * @param request
     * @param
     * @return merchantNo
     */
    public String response(String request, String type) throws IOException {
        log.info("易宝回调类型:{},密文:{}",type,request);
        //获取回调数据
        DigitalEnvelopeDTO dto = new DigitalEnvelopeDTO();
        dto.setCipherText(request);
        try {
            //设置商户私钥
            PrivateKey privateKey = InternalConfig.getISVPrivateKey(CertTypeEnum.RSA2048);
            //设置易宝公钥
            PublicKey publicKey = InternalConfig.getYopPublicKey(CertTypeEnum.RSA2048);
            //解密验签
            dto = DigitalEnvelopeUtils.decrypt(dto, privateKey, publicKey);
            log.info("易宝回调:{}",dto.getPlainText());
            if(NOTIFY_TYPE_REG.equals(type)) robot.sendMsg(dto.getPlainText());
            if(NOTIFY_TYPE_PAY.equals(type)) payService.payFinish(mapper.readValue(dto.getPlainText(), YiBaoPayRespDTO.class));
            if(NOTIFY_TYPE_REFUND.equals(type)) payService.refundFinish(mapper.readValue(dto.getPlainText(), YiBaoRefundResp3DTO.class));
            if(NOTIFY_TYPE_DIVIDE.equals(type)) payService.tradedivideFinish(mapper.readValue(dto.getPlainText(), TradedivideResp2DTO.class));
        } catch (Exception e) {
            log.error("易宝回调解析错误:{}",e);
        }
        return YiBaoService.YIBAO_SUCCESS;
    }

}
