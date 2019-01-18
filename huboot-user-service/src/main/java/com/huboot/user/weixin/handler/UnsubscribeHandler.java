package com.huboot.user.weixin.handler;

import com.huboot.user.weixin.service.IWxmpUserService;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class UnsubscribeHandler extends AbstractHandler {

    @Autowired
    private IWxmpUserService wxmpUserService;


    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                  Map<String, Object> context, WxMpService wxMpService,
                                  WxSessionManager sessionManager) {

        String openId = wxMessage.getFromUser();
        String originalId = wxMessage.getToUser();
        this.logger.info("取消关注用户 OPENID: " + openId + " 公帐号ID：" + originalId);

        try {
            wxmpUserService.updateWeixinUserOnUnSubscribe(openId);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        return null;
    }

}
