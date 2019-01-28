package com.huboot.share.file_service.api.builder;

import com.huboot.share.common.constant.ServiceName;
import com.huboot.share.common.feign.RequestHeaderInterceptor;
import com.huboot.share.common.feign.ResultErrorDecoder;
import com.huboot.share.file_service.api.feign.FileFeignClient;
import feign.Client;
import feign.Feign;
import feign.codec.Decoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.cloud.openfeign.encoding.FeignClientEncodingProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Administrator on 2018/9/20 0020.
 */
@Configuration
@EnableConfigurationProperties(FeignClientEncodingProperties.class)
@Import(FeignClientsConfiguration.class)
public class FileFeignClientBuilder {

    @Autowired
    private FeignClientEncodingProperties properties;

    @Bean
    public FileFeignClient fileFeignClient(Decoder decoder, Client client) {
        return Feign.builder()
                .client(client)
                .encoder(new SpringFormEncoder())
                .decoder(decoder)
                .errorDecoder(new ResultErrorDecoder())
                .requestInterceptor(new RequestHeaderInterceptor(properties))
                .target(FileFeignClient.class, "http://" + ServiceName.FILE_SERVICE.toUpperCase());
    }
}
