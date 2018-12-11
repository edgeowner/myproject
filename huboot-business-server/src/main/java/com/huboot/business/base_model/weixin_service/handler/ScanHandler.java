package com.huboot.business.base_model.weixin_service.handler;

import com.huboot.business.base_model.weixin_service.service.IWeixinPublicService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class ScanHandler extends AbstractHandler {

    @Autowired
    private IWeixinPublicService weixinPublicService;


    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        String openId = wxMessage.getFromUser();
        String originalId = wxMessage.getToUser();
        String eventKey = wxMessage.getEventKey();

        if(StringUtils.isEmpty(eventKey)) {
            logger.error("扫码eventKey为空");
            return null;
        }

        weixinPublicService.handleSacnAEvent(originalId, openId, eventKey);

        return null;
    }


}
