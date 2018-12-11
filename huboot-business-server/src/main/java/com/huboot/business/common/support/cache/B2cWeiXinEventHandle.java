package com.huboot.business.common.support.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 监听微信模块的时间,并做对应的响应
 * **/
@Component
public class B2cWeiXinEventHandle {

    private Logger logger = LoggerFactory.getLogger(B2cWeiXinEventHandle.class);

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 收到消息
     * **/
    public void onMessage(String message){
        /*if(logger.isDebugEnabled()) logger.debug("b2c收到信息:"+message);
       // B2cWeixinEventRootDTO b2cWeixinEventRootDTO = null;
        try {
            //这特么是什么鬼。。。
            message = message.substring(1, message.length() - 1);
            message = message.replace("\\", "");
            b2cWeixinEventRootDTO = objectMapper.readValue(message, B2cWeixinEventRootDTO.class);
        } catch (IOException e) {
            logger.error("解析异常", e);
            return;
        }
        switch (b2cWeixinEventRootDTO.getWeixinEventEnum()){
            case afterAuditing:
                b2CShopService.bindShopUseWeixinUid(b2cWeixinEventRootDTO.getShopUid(),b2cWeixinEventRootDTO.getWeixinUid());
                break;
            case subscribeWeixin:
                subscribeWeixin(b2cWeixinEventRootDTO);
                break;
            case cancelSubscribeWeixin:
                cancelSubscribeWeixin(b2cWeixinEventRootDTO);
                break;
            case cancellationAuthorization:
                cancellationAuthorization(b2cWeixinEventRootDTO.getWeixinUid());
                break;
            case cancelMiniappAuthorization:
                cancelMiniappAuthorization(b2cWeixinEventRootDTO.getWeixinUid());
                break;
            default:
                logger.warn("微信通知事件没有匹配的handle");
                break;
        }
*/
    }

    /***
     *
     * 说明:用户订阅微信公众号
     * **/
   /* private void subscribeWeixin(B2cWeixinEventRootDTO b2cWeixinEventRootDTO){
        logger.info("用户订阅微信公众号:{}",b2cWeixinEventRootDTO.toString());
        b2CShopService.bindUserOpenIdToShop(b2cWeixinEventRootDTO.getPhone(),b2cWeixinEventRootDTO.getOpenUid(),b2cWeixinEventRootDTO.getCompanyUid());
    }


    *//**
     *说明:用户取消订阅微信公众号
     * **//*
    private void cancelSubscribeWeixin(B2cWeixinEventRootDTO b2cWeixinEventRootDTO){
        logger.info("用户取消订阅微信公众号:{}",b2cWeixinEventRootDTO.toString());
        b2CShopService.unBindUserOpenIdToShop(b2cWeixinEventRootDTO.getOpenUid());
    }

    *//**
     * 商户取消微信授权，通知b2c系统
     * **//*
    private void cancellationAuthorization(String weixinUid){
        logger.info("商户取消微信授权weixinUid:{}",weixinUid);
        b2CShopService.cancellationAuthorization(weixinUid);
    }

    *//**
     * 商户取消微信授权，通知b2c系统
     * **//*
    private void cancelMiniappAuthorization(String weixinUid){
        logger.info("商户取消小程序授权weixinUid:{}",weixinUid);
        b2CShopService.cancelMiniappAuthorization(weixinUid);
    }*/


}
