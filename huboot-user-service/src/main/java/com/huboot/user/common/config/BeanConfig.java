package com.huboot.user.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by Administrator on 2018/12/5 0005.
 */
@Configuration
public class BeanConfig {

    /**
     * 异步任务
     * @return
     */
    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(100);
        taskExecutor.setKeepAliveSeconds(3000);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

}
