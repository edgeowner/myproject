package com.huboot.business.base_model.weixin_service.support;

import com.huboot.business.base_model.weixin_service.config.WechatOpenProperties;
import com.huboot.business.base_model.weixin_service.config.WxOpenInRedisConfigStorageExt;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.api.impl.WxOpenServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnClass(WxOpenService.class)
@EnableConfigurationProperties(WechatOpenProperties.class)
public class WechatOpenFactory {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WechatOpenProperties wechatOpenProperties;
    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    @ConditionalOnMissingBean
    public WxOpenService wxOpenService() {
        WxOpenInRedisConfigStorageExt inRedisConfigStorage = new WxOpenInRedisConfigStorageExt(redisTemplate);
        inRedisConfigStorage.setComponentAppId(wechatOpenProperties.getComponentAppId());
        inRedisConfigStorage.setComponentAppSecret(wechatOpenProperties.getComponentSecret());
        inRedisConfigStorage.setComponentToken(wechatOpenProperties.getComponentToken());
        inRedisConfigStorage.setComponentAesKey(wechatOpenProperties.getComponentAesKey());
        WxOpenService wxOpenService = new WxOpenServiceImpl();
        wxOpenService.setWxOpenConfigStorage(inRedisConfigStorage);
        return wxOpenService;
    }

}
