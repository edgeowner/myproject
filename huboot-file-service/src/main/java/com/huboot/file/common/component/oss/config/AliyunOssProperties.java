package com.huboot.file.common.component.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOssProperties {

    private String outEndpoint;
    private String internalEndpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

}
