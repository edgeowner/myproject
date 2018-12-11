package com.huboot.business.common.config;

import com.huboot.business.base_model.login.sso.client.support.CustomErrorHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class HttpConfig {

    @Value("${http.maxTotal:30}")
    private int maxTotal;

    @Value("${http.maxPerRoute:30}")
    private int maxPerRoute;

    @Value("${http.connectTimeout:30000}")
    private int connectTimeout;

    @Value("${http.readTimeout:30000}")
    private int readTimeout;

    @Value("${http.requestTimeout:15000}")
    private int requestTimeout;

    @Bean
    @ConditionalOnMissingBean
    public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory(){
        // 长连接保持30秒
        PoolingHttpClientConnectionManager pollingConnectionManager = new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
        pollingConnectionManager.setMaxTotal(maxTotal);
        pollingConnectionManager.setDefaultMaxPerRoute(maxPerRoute);

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setConnectionManager(pollingConnectionManager);
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(2, true));
        // 保持长连接配置，需要在头添加Keep-Alive
        httpClientBuilder.setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE);
        RequestConfig.Builder builder = RequestConfig.custom();
        builder.setConnectionRequestTimeout(200);
        builder.setConnectTimeout(5000);
        builder.setSocketTimeout(5000);
        RequestConfig requestConfig = builder.build();
        httpClientBuilder.setDefaultRequestConfig(requestConfig);

        CloseableHttpClient httpClient = httpClientBuilder.build();

        // httpClient连接配置，底层是配置RequestConfig
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        clientHttpRequestFactory.setConnectTimeout(connectTimeout);
        clientHttpRequestFactory.setReadTimeout(readTimeout);
        clientHttpRequestFactory.setConnectionRequestTimeout(requestTimeout);
         clientHttpRequestFactory.setBufferRequestBody(false);
        return clientHttpRequestFactory;
    }

    @Bean
    @ConditionalOnMissingBean(value= RestTemplate.class)
    public RestTemplate customRestTemplate(CustomErrorHandler customErrorHandler) {
        RestTemplate restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory());
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        List<HttpMessageConverter<?>> converters2 = new LinkedList<>();
        converters.forEach(c -> {
            if (c instanceof StringHttpMessageConverter) {
                converters2.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
            } else {
                converters2.add(c);
            }
        });
        restTemplate.setMessageConverters(converters2);
        restTemplate.setErrorHandler(customErrorHandler);
        return restTemplate;
    }


}
