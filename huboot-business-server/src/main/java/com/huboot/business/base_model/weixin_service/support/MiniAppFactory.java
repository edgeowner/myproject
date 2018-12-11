package com.huboot.business.base_model.weixin_service.support;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaKefuMessage;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import cn.binarywang.wx.miniapp.message.WxMaMessageHandler;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import com.google.common.collect.Lists;
import com.huboot.business.base_model.weixin_service.entity.WeixinPublicEntity;
import com.huboot.business.base_model.weixin_service.repository.IWeixinPublicRepository;
import com.huboot.business.common.component.exception.BizException;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Component
@ConditionalOnClass(WxMaService.class)
public class MiniAppFactory {

    @Autowired
    private IWeixinPublicRepository weixinPublicRepository;
    @Autowired
    private WxOpenService wxOpenService;


    public WxMaService getMaService(String weixinUid) {
        if(StringUtils.isEmpty(weixinUid)) {
            throw new BizException("微信uid不能为空");
        }
        WeixinPublicEntity publicEntity = weixinPublicRepository.findByWeixinUid(weixinUid);
        return this.getMaService(publicEntity);
    }

    public WxMaService getMaService(WeixinPublicEntity publicEntity) {
        if(publicEntity == null) {
            throw new BizException("微信配置不存在");
        }
        if(!WeixinPublicEntity.TypeEnum.miniapp.equals(publicEntity.getType())) {
            throw new BizException("微信uid:" + publicEntity.getWeixinUid() + "不是小程序");
        }
        if(WeixinPublicEntity.BindTypeEnum.developer.equals(publicEntity.getBindType())) {
            WxMaInMemoryConfig config = new WxMaInMemoryConfig();
            config.setAppid(publicEntity.getAppId());
            config.setSecret(publicEntity.getSecret());
            config.setToken(publicEntity.getToken());
            config.setAesKey(publicEntity.getAesKey());
            config.setMsgDataFormat("JSON");
            WxMaService service = new WxMaServiceImpl();
            service.setWxMaConfig(config);
            return service;
        } else if(WeixinPublicEntity.BindTypeEnum.weixin3open.equals(publicEntity.getBindType())) {
            if(WeixinPublicEntity.StatusEnum.unAuthorized.equals(publicEntity.getStatus())) {
                throw new BizException("微信uid:" + publicEntity.getWeixinUid() + "公众号已经取消授权给开放平台");
            }
            return wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(publicEntity.getAppId());
        }else {
            throw new BizException("微信uid:" + publicEntity.getWeixinUid() + "绑定类型错误");
        }
    }


    private static final WxMaMessageHandler templateMsgHandler = (wxMessage, context, service, sessionManager) ->
            service.getMsgService().sendTemplateMsg(WxMaTemplateMessage.builder()
                    .templateId("此处更换为自己的模板id")
                    .formId("自己替换可用的formid")
                    .data(Lists.newArrayList(
                            new WxMaTemplateMessage.Data("keyword1", "339208499", "#173177")))
                    .toUser(wxMessage.getFromUser())
                    .build());

    private final WxMaMessageHandler logHandler = (wxMessage, context, service, sessionManager) -> {
        System.out.println("收到消息：" + wxMessage.toString());
        service.getMsgService().sendKefuMsg(WxMaKefuMessage.newTextBuilder().content("收到信息为：" + wxMessage.toJson())
                .toUser(wxMessage.getFromUser()).build());
    };

    private final WxMaMessageHandler textHandler = (wxMessage, context, service, sessionManager) ->
            service.getMsgService().sendKefuMsg(WxMaKefuMessage.newTextBuilder().content("回复文本消息")
                    .toUser(wxMessage.getFromUser()).build());

    private final WxMaMessageHandler picHandler = (wxMessage, context, service, sessionManager) -> {
        try {
            WxMediaUploadResult uploadResult = service.getMediaService()
                    .uploadMedia("image", "png",
                            ClassLoader.getSystemResourceAsStream("tmp.png"));
            service.getMsgService().sendKefuMsg(
                    WxMaKefuMessage
                            .newImageBuilder()
                            .mediaId(uploadResult.getMediaId())
                            .toUser(wxMessage.getFromUser())
                            .build());
        } catch (WxErrorException e) {

        }
    };

    private final WxMaMessageHandler qrcodeHandler = (wxMessage, context, service, sessionManager) -> {
        try {
            final File file = service.getQrcodeService().createQrcode("123", 430);
            WxMediaUploadResult uploadResult = service.getMediaService().uploadMedia("image", file);
            service.getMsgService().sendKefuMsg(
                    WxMaKefuMessage
                            .newImageBuilder()
                            .mediaId(uploadResult.getMediaId())
                            .toUser(wxMessage.getFromUser())
                            .build());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    };

    public WxMaMessageRouter router(WxMaService service) {
        final WxMaMessageRouter router = new WxMaMessageRouter(service);
        router
                .rule().handler(logHandler).next()
                .rule().async(false).content("模板").handler(templateMsgHandler).end()
                .rule().async(false).content("文本").handler(textHandler).end()
                .rule().async(false).content("图片").handler(picHandler).end()
                .rule().async(false).content("二维码").handler(qrcodeHandler).end();
        return router;
    }

}
