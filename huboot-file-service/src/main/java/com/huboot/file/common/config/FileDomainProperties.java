package com.huboot.file.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "file.domain")
public class FileDomainProperties {
    /**
     * 文件存储域名
     */
    private String domain;

}
