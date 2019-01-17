package com.huboot.commons.config;

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
    public static final String TOKEN_HEAD = "Bearer ";
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
     * 定时任务
     */
    public static final String TASK_SERVICE_SECRET = "TaskServiceJwtSecret888";
    /**
     * 加密字符串
     * 匿名用户
     */
    public static final String ANONYMOUS_SECRET = "AnonymousJwtSecret999999";
    /**
     * 单位秒，默认1天
     * 微服务之间的请求，都是及时的。会话暂时保留一天。
     */
    private Long taskServiceExpiration = 1 * 24 * 60 * 60L;

    /**
     * 单位秒，默认1天
     * 微服务之间的请求，都是及时的。会话暂时保留一天。
     */
    private Long anonymousExpiration = 1 * 24 * 60 * 60L;

}
