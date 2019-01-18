package com.huboot.user.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Administrator on 2018/9/12 0012.
 */
@Data
@ConfigurationProperties(prefix = "baidu.ocr")
public class BaiduOCRProperties {

    private String appId;

    private String secretID;

    private String secretKey;

}
