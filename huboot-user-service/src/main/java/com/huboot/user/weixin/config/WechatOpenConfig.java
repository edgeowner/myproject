package com.huboot.user.weixin.config;

import com.huboot.user.weixin.handler.*;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.constant.WxMpEventConstants;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.api.impl.WxOpenMessageRouter;
import me.chanjar.weixin.open.api.impl.WxOpenServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by Administrator on 2018/9/4 0004.
 */
@Configuration
@EnableConfigurationProperties(WechatOpenProperties.class)
public class WechatOpenConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WechatOpenProperties openProperties;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private NullHandler nullHandler;
    @Autowired
    private KfSessionHandler kfSessionHandler;
    @Autowired
    private StoreCheckNotifyHandler storeCheckNotifyHandler;
    @Autowired
    private LocationHandler locationHandler;
    @Autowired
    private MenuHandler menuHandler;
    @Autowired
    private MsgHandler msgHandler;
    @Autowired
    private ScanHandler scanHandler;
    @Autowired
    private UnsubscribeHandler unsubscribeHandler;
    @Autowired
    private SubscribeHandler subscribeHandler;

    @Bean
    @ConditionalOnMissingBean
    public WxOpenService wxOpenService() {
        WxOpenRedisConfigStorage redisConfigStorage = new WxOpenRedisConfigStorage(redisTemplate);
        redisConfigStorage.setComponentAppId(openProperties.getComponentAppId());
        redisConfigStorage.setComponentAppSecret(openProperties.getComponentSecret());
        redisConfigStorage.setComponentToken(openProperties.getComponentToken());
        redisConfigStorage.setComponentAesKey(openProperties.getComponentAesKey());
        WxOpenService wxOpenService = new WxOpenServiceImpl();
        wxOpenService.setWxOpenConfigStorage(redisConfigStorage);
        return wxOpenService;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxOpenMessageRouter getWxOpenMessageRouter(WxOpenService wxOpenService){
        WxOpenMessageRouter newRouter = new WxOpenMessageRouter(wxOpenService);

        newRouter.rule().handler((wxMpXmlMessage, map, wxMpService, wxSessionManager) -> {
            logger.info("接收到 {} 公众号请求消息，内容：{}", wxMpService.getWxMpConfigStorage().getAppId(), wxMpXmlMessage);
            return null;
        }).next();

        // 接收客服会话管理事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxMpEventConstants.CustomerService.KF_CREATE_SESSION)
                .handler(this.kfSessionHandler).end();
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxMpEventConstants.CustomerService.KF_CLOSE_SESSION)
                .handler(this.kfSessionHandler)
                .end();
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxMpEventConstants.CustomerService.KF_SWITCH_SESSION)
                .handler(this.kfSessionHandler).end();

        // 门店审核事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxMpEventConstants.POI_CHECK_NOTIFY)
                .handler(this.storeCheckNotifyHandler).end();

        // 自定义菜单事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.MenuButtonType.CLICK).handler(this.menuHandler).end();

        // 点击菜单连接事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.MenuButtonType.VIEW).handler(this.nullHandler).end();

        // 关注事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.SUBSCRIBE).handler(this.subscribeHandler)
                .end();

        // 取消关注事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.UNSUBSCRIBE)
                .handler(this.unsubscribeHandler).end();

        // 上报地理位置事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.LOCATION).handler(this.locationHandler)
                .end();

        // 接收地理位置消息
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.LOCATION)
                .handler(this.locationHandler).end();

        // 扫码事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.SCAN).handler(this.scanHandler).end();

        // 默认
        newRouter.rule().async(false).handler(this.msgHandler).end();

        return newRouter;
    }
}
