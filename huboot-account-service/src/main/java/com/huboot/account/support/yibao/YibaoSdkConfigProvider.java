package com.huboot.account.support.yibao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.huboot.commons.component.exception.BizException;
import com.yeepay.g3.sdk.yop.config.AppSdkConfig;
import com.yeepay.g3.sdk.yop.config.AppSdkConfigProvider;
import com.yeepay.g3.sdk.yop.config.SDKConfig;
import com.yeepay.g3.sdk.yop.config.provider.BaseFixedAppSdkConfigProvider;
import com.yeepay.g3.sdk.yop.config.provider.support.AppSdkConfigInitTask;
import com.yeepay.g3.sdk.yop.config.support.CheckUtils;
import com.yeepay.g3.sdk.yop.utils.Holder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class YibaoSdkConfigProvider implements AppSdkConfigProvider {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Map<String, Holder<AppSdkConfig>> configs = Maps.newHashMap();

    private Holder<AppSdkConfig> defaultConfig = null;

    private volatile boolean init = false;

    private ObjectMapper mapper;

    private String configStr;

    public YibaoSdkConfigProvider( ObjectMapper mapper,String config){
        this.mapper = mapper;
        this.configStr = config;
    }


    protected List<SDKConfig> loadCustomSdkConfig() {
        try {
            return Arrays.asList(mapper.readValue(configStr,SDKConfig.class));
        } catch (IOException e) {
            logger.error("读取配置文件失败:{}",e);
        }
        return null;
    }

    public final AppSdkConfig getConfig(String appKey) {
        if (!this.init) {
            this.init();
        }

        Holder<AppSdkConfig> holder = (Holder)this.configs.get(appKey);
        return holder == null ? null : (AppSdkConfig)holder.getValue();
    }

    public final AppSdkConfig getDefaultConfig() {
        if (!this.init) {
            this.init();
        }

        return this.defaultConfig == null ? null : (AppSdkConfig)this.defaultConfig.getValue();
    }

    public AppSdkConfig getConfigWithDefault(String appKey) {
        if (!this.init) {
            this.init();
        }

        Holder<AppSdkConfig> holder = (Holder)this.configs.get(appKey);
        return holder == null ? (this.defaultConfig == null ? null : (AppSdkConfig)this.defaultConfig.getValue()) : (AppSdkConfig)holder.getValue();
    }

    private synchronized void init() {
        if (!this.init) {
            List<SDKConfig> customSdkConfigs = this.loadCustomSdkConfig();
            if (customSdkConfigs != null && customSdkConfigs.size() != 0) {
                boolean hasDefault = false;
                Iterator var3 = customSdkConfigs.iterator();

                while(var3.hasNext()) {
                    SDKConfig sdkConfig = (SDKConfig)var3.next();
                    CheckUtils.checkCustomSDKConfig(sdkConfig);
                    Holder<AppSdkConfig> holder = new Holder(new AppSdkConfigInitTask(sdkConfig));
                    this.configs.put(sdkConfig.getAppKey(), holder);
                    if (BooleanUtils.isTrue(sdkConfig.getDefaulted()) && !hasDefault) {
                        this.defaultConfig = holder;
                        hasDefault = true;
                    }
                }

                if (!hasDefault) {
                    this.defaultConfig = (Holder)this.configs.get(((SDKConfig)customSdkConfigs.get(0)).getAppKey());
                }
            } else {
                throw new BizException("初始化易宝配置异常");
            }

            this.init = true;
        }
    }

}
