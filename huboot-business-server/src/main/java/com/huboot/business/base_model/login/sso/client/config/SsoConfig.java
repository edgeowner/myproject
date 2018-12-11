package com.huboot.business.base_model.login.sso.client.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.huboot.business.base_model.login.sso.client.support.CustomErrorHandler;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Configuration
public class SsoConfig {

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
	public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory() {
		// 长连接保持30秒
		PoolingHttpClientConnectionManager pollingConnectionManager = new PoolingHttpClientConnectionManager(30,
				TimeUnit.SECONDS);
		pollingConnectionManager.setMaxTotal(maxTotal);
		pollingConnectionManager.setDefaultMaxPerRoute(maxPerRoute);

		HttpClientBuilder httpClientBuilder = HttpClients.custom();
		httpClientBuilder.setConnectionManager(pollingConnectionManager);
		httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(2, true));
		// 保持长连接配置，需要在头添加Keep-Alive
		httpClientBuilder.setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE);
		// RequestConfig.Builder builder = RequestConfig.custom();
		// builder.setConnectionRequestTimeout(200);
		// builder.setConnectTimeout(5000);
		// builder.setSocketTimeout(5000);
		// RequestConfig requestConfig = builder.build();
		// httpClientBuilder.setDefaultRequestConfig(requestConfig);

		HttpClient httpClient = httpClientBuilder.build();

		// httpClient连接配置，底层是配置RequestConfig
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
				httpClient);
		clientHttpRequestFactory.setConnectTimeout(connectTimeout);
		clientHttpRequestFactory.setReadTimeout(readTimeout);
		clientHttpRequestFactory.setConnectionRequestTimeout(requestTimeout);
		// clientHttpRequestFactory.setBufferRequestBody(false);
		return clientHttpRequestFactory;
	}

	@Bean
	@ConditionalOnMissingBean(value = RestTemplate.class)
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

	@Bean("objectMapper")
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		// 忽略json字符串中不识别的属性
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 忽略无法转换的对象 “No serializer found for class com.xxx.xxx”
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
		//取消timestamps形式
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.findAndRegisterModules();
		return objectMapper;
	}

	@Bean
	@ConditionalOnMissingBean
	public PathMatchingResourcePatternResolver getPathMatchingResourcePatternResolver() {
		return new PathMatchingResourcePatternResolver();
	}
}
