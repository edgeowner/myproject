package com.huboot.business.base_model.pay.config.pingpp;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ping++ properties
 */
@ConfigurationProperties(prefix = "pay.pingpp2")
public class PingppPropertiesMiniRisk {
    /**
     * 设置Ping++apiKey
     */
    private String apiKey;

    /**
     * 设置Ping++的appId
     */
    private String appId;

    /**
     * 设置Ping+= privateKeyFilePath
     */
    private String privateKeyFilePath;
    /**
     * 设置Ping+= pubKeyPath
     */
    private String pubKeyPath;

    //支付成功的回调地址。
    private String successUrl;

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPrivateKeyFilePath() {
        return privateKeyFilePath;
    }

    public void setPrivateKeyFilePath(String privateKeyFilePath) {
        this.privateKeyFilePath = privateKeyFilePath;
    }


    public String getPubKeyPath() {
        return pubKeyPath;
    }

    public void setPubKeyPath(String pubKeyPath) {
        this.pubKeyPath = pubKeyPath;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }
}
