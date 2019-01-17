package com.huboot.commons.component.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@Data
@ConfigurationProperties(prefix = "xiehua.jwt")
public class JwtProperties implements Serializable {
    /**
     * httpHeader的key
     */
    public static final String HTTP_HEADER = "Authorization";
    /**
     * token的头部
     */
    private static final String TOKEN_HEAD = "Bearer ";
    /**
     * 加密字符串
     */
    public static final String USER_SECRET = "1U6gJIandTOyMfyFGYLV9uwQTvNtS71HapJ2nBltitLTNAzgkxAS";
    /**
     * 单位秒，默认7天
     */
    private Long userExpiration = 7 * 24 * 60 * 60L;
    /**
     * 加密字符串
     * 微服务
     */
    public static final String MICROSERVICE_SECRET = "MicroServiceJwtSecret888";

    /**
     * 单位秒，默认1天
     * 微服务之间的请求，都是及时的。会话暂时保留一天。
     */
    private Long microServiceExpiration = 1 * 24 * 60 * 60L;


}
