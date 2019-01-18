package com.huboot.user.weixin.handler;

import com.huboot.user.weixin.service.IWxmpUserService;
import me.chanjar.weixin.common.error.WxErrorException;
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
public class SubscribeHandler extends AbstractHandler {

    @Autowired
    private IWxmpUserService wxmpUserService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                Map<String, Object> context, WxMpService weixinService,
                                WxSessionManager sessionManager) throws WxErrorException {

        String openId = wxMessage.getFromUser();
        String originalId = wxMessage.getToUser();
        this.logger.info("关注用户 OPENID: " + openId + " 公帐号原始ID：" + originalId + " EventKey:" + wxMessage.getEventKey());

        try {
            return new TextBuilder().build(wxmpUserService.saveWeixinUserOnSubscribe(originalId, openId, wxMessage.getEventKey(), weixinService)
                    , wxMessage, weixinService);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        return null;
    }

}
