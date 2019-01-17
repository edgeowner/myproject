package com.huboot.share.common.feign;

import com.huboot.commons.component.auth.JwtTokenComponent;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.encoding.FeignClientEncodingProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2018/2/23 0023.
 */
@Configuration
@EnableConfigurationProperties(FeignClientEncodingProperties.class)
public class FeignCilentConfig {

    @Autowired
    private JwtTokenComponent jwtTokenComponent;

    @Bean
    public RequestHeaderInterceptor requestHeaderInterceptor(FeignClientEncodingProperties properties) {
        RequestHeaderInterceptor interceptor = new RequestHeaderInterceptor(properties);
        interceptor.setJwtTokenComponent(jwtTokenComponent);
        return interceptor;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new ResultErrorDecoder();
    }

}
