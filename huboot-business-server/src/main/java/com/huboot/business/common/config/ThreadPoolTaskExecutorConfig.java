package com.huboot.business.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by Administrator on 2018/1/17 0017.
 */
@Configuration
public class ThreadPoolTaskExecutorConfig {

    /**
     * 短信发送线程池
     * @return
     */
    @Bean
    public ThreadPoolTaskExecutor smsTaskExecutor() {
        ThreadPoolTaskExecutor smsTaskExecutor = new ThreadPoolTaskExecutor();
        smsTaskExecutor.setCorePoolSize(5);
        smsTaskExecutor.setMaxPoolSize(10);
        smsTaskExecutor.setQueueCapacity(100);
        smsTaskExecutor.setKeepAliveSeconds(3000);
        return smsTaskExecutor;
    }

    /**
     * 微信消息发送线程池
     * @return
     */
    @Bean
    public ThreadPoolTaskExecutor weixinTaskExecutor() {
        ThreadPoolTaskExecutor weixinTaskExecutor = new ThreadPoolTaskExecutor();
        weixinTaskExecutor.setCorePoolSize(5);
        weixinTaskExecutor.setMaxPoolSize(10);
        weixinTaskExecutor.setQueueCapacity(100);
        weixinTaskExecutor.setKeepAliveSeconds(3000);
        return weixinTaskExecutor;
    }

    /**
     * 微信消息发送线程池
     * @return
     */
    @Bean
    public ThreadPoolTaskExecutor orderAsync() {
        ThreadPoolTaskExecutor orderAsync = new ThreadPoolTaskExecutor();
        orderAsync.setCorePoolSize(5);
        orderAsync.setMaxPoolSize(10);
        orderAsync.setQueueCapacity(100);
        orderAsync.setKeepAliveSeconds(3000);
        return orderAsync;
    }
}
