package com.huboot.user.weixin.support;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import com.huboot.user.weixin.config.WechatMiniappProperties;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.open.api.WxOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2018/9/19 0019.
 */
@Component
@EnableConfigurationProperties(WechatMiniappProperties.class)
public class WxServiceFactory {

    @Autowired
    private WechatMiniappProperties miniappProperties;

    @Autowired
    private WxOpenService wxOpenService;

    public WxMaService getWxMaService(String appId) {
        List<WechatMiniappProperties.Miniapp> list = miniappProperties.getMiniappList();
        for(WechatMiniappProperties.Miniapp miniapp : list) {
            if(miniapp.getAppid().equals(appId)) {
                WxMaInMemoryConfig config = new WxMaInMemoryConfig();
                config.setAppid(miniapp.getAppid());
                config.setSecret(miniapp.getSecret());
                config.setMsgDataFormat("JSON");
                WxMaService service = new WxMaServiceImpl();
                service.setWxMaConfig(config);
                return service;
            }
        }
        return wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appId);
    }

    public WxMpService getWxMpService(String appId) {
        return wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid(appId);
    }

}
