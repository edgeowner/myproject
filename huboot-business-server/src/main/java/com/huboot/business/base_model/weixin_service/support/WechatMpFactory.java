package com.huboot.business.base_model.weixin_service.support;

import com.huboot.business.base_model.weixin_service.entity.WeixinPublicEntity;
import com.huboot.business.base_model.weixin_service.handler.*;
import com.huboot.business.base_model.weixin_service.config.WxMpInRedisConfigStorageExt;
import com.huboot.business.base_model.weixin_service.repository.IWeixinPublicRepository;
import com.huboot.business.common.component.exception.BizException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.constant.WxMpEventConstants;
import me.chanjar.weixin.open.api.WxOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static me.chanjar.weixin.common.api.WxConsts.*;

@Component
public class WechatMpFactory {

    @Autowired
    protected LogHandler logHandler;
    @Autowired
    protected NullHandler nullHandler;
    @Autowired
    protected KfSessionHandler kfSessionHandler;
    @Autowired
    protected StoreCheckNotifyHandler storeCheckNotifyHandler;
    @Autowired
    private LocationHandler locationHandler;
    @Autowired
    private MenuHandler menuHandler;
    @Autowired
    private MsgHandler msgHandler;
    @Autowired
    private ScanHandler scanHandler;
    @Autowired
    private ZKUnsubscribeHandler unsubscribeHandler;
    @Autowired
    private ZKSubscribeHandler subscribeHandler;
    @Autowired
    private IWeixinPublicRepository weixinPublicRepository;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private WxOpenService wxOpenService;

    /**
     * 从原始公众号获取安全WxMpService
     * @param weixinUid
     * @return
     */
    public WxMpService getDeveloperWXMpService(String weixinUid) {
        if(StringUtils.isEmpty(weixinUid)) {
            throw new BizException("微信uid不能为空");
        }
        WeixinPublicEntity configEntity = weixinPublicRepository.findByWeixinUid(weixinUid);
        return getDeveloperWxMpService(configEntity);
    }

    /**
     * 从原始公众号获取安全WxMpService
     * @param configEntity
     * @return
     */
    public WxMpService getDeveloperWxMpService(WeixinPublicEntity configEntity) {
        if(configEntity == null) {
            throw new BizException("微信配置不存在");
        }
        if(!WeixinPublicEntity.StatusEnum.validServerSuccess.equals(configEntity.getStatus())) {
            throw new BizException("微信uid:" + configEntity.getWeixinUid() + "账号未验证");
        }
        return getDeveloperWxMpService1(configEntity);
    }

    /**
     * 从原始公众号或者微信开放平台获取安全WxMpService
     * @param configEntity
     * @return
     */
    public WxMpService getWXMpService(WeixinPublicEntity configEntity) {
        if(configEntity == null) {
            throw new BizException("微信uid:" + configEntity.getWeixinUid() + "配置不存在");
        }
        if(WeixinPublicEntity.BindTypeEnum.developer.equals(configEntity.getBindType())) {
            if(!WeixinPublicEntity.StatusEnum.validServerSuccess.equals(configEntity.getStatus())) {
                throw new BizException("微信uid:" + configEntity.getWeixinUid() + "账号未验证");
            }
            return getDeveloperWxMpService1(configEntity);
        } else if(WeixinPublicEntity.BindTypeEnum.weixin3open.equals(configEntity.getBindType())) {
            if(!WeixinPublicEntity.TypeEnum.pubapp.equals(configEntity.getType())) {
                throw new BizException("微信uid:" + configEntity.getWeixinUid() + "不是公众号");
            }
            if(WeixinPublicEntity.StatusEnum.unAuthorized.equals(configEntity.getStatus())) {
                throw new BizException("微信uid:" + configEntity.getWeixinUid() + "公众号已经取消授权给开放平台");
            }
            return wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid(configEntity.getAppId());
        } else {
            throw new BizException("微信uid:" + configEntity.getWeixinUid() + "绑定类型错误");
        }
    }

    /**
     * 没校验的WXMpService，不安全使用
     * @param weixinUid
     * @return
     */
    public WxMpService getDeveloperWXMpServiceWithNotValid(String weixinUid) {
        if(StringUtils.isEmpty(weixinUid)) {
            throw new BizException("微信uid不能为空");
        }
        WeixinPublicEntity configEntity = weixinPublicRepository.findByWeixinUid(weixinUid);
        if(configEntity == null) {
            throw new BizException("微信uid:" + weixinUid + "配置不存在");
        }
        return getDeveloperWxMpService1(configEntity);
    }

    private WxMpService getDeveloperWxMpService1(WeixinPublicEntity configEntity) {
        if(!WeixinPublicEntity.BindTypeEnum.developer.equals(configEntity.getBindType())) {
            throw new BizException("微信uid:" + configEntity.getWeixinUid() + "账号绑定平台不正确");
        }
        if(!WeixinPublicEntity.TypeEnum.pubapp.equals(configEntity.getType())) {
            throw new BizException("微信uid:" + configEntity.getWeixinUid() + "不是公众号");
        }
        WxMpInRedisConfigStorageExt configStorage = new WxMpInRedisConfigStorageExt(redisTemplate);
        configStorage.setAppId(configEntity.getAppId());
        configStorage.setSecret(configEntity.getSecret());
        configStorage.setToken(configEntity.getToken());
        configStorage.setAesKey(configEntity.getAesKey());
        configStorage.setWeixinUid(configEntity.getWeixinUid());
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(configStorage);
        return wxMpService;
    }

    /**
     * 获取微信处理路由
     * @param wxMpService
     * @return
     */
    public WxMpMessageRouter createRouter(WxMpService wxMpService) {
        WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);
        // 记录所有事件的日志 （异步执行）
        newRouter.rule().handler(this.logHandler).next();

        // 接收客服会话管理事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
            .event(WxMpEventConstants.CustomerService.KF_CREATE_SESSION)
            .handler(this.kfSessionHandler).end();
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
            .event(WxMpEventConstants.CustomerService.KF_CLOSE_SESSION)
            .handler(this.kfSessionHandler)
            .end();
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
            .event(WxMpEventConstants.CustomerService.KF_SWITCH_SESSION)
            .handler(this.kfSessionHandler).end();

        // 门店审核事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
            .event(WxMpEventConstants.POI_CHECK_NOTIFY)
            .handler(this.storeCheckNotifyHandler).end();

        // 自定义菜单事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
            .event(MenuButtonType.CLICK).handler(this.menuHandler).end();

        // 点击菜单连接事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
            .event(MenuButtonType.VIEW).handler(this.nullHandler).end();

        // 关注事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
            .event(EventType.SUBSCRIBE).handler(this.subscribeHandler)
            .end();

        // 取消关注事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
            .event(EventType.UNSUBSCRIBE)
            .handler(this.unsubscribeHandler).end();

        // 上报地理位置事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
            .event(EventType.LOCATION).handler(this.locationHandler)
            .end();

        // 接收地理位置消息
        newRouter.rule().async(false).msgType(XmlMsgType.LOCATION)
            .handler(this.locationHandler).end();

        // 扫码事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
            .event(EventType.SCAN).handler(this.scanHandler).end();

        // 默认
        newRouter.rule().async(false).handler(this.msgHandler).end();

        return newRouter;
    }



}
