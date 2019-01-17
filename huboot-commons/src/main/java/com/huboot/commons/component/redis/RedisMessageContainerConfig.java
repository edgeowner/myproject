package com.huboot.commons.component.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2019/1/9 0009.
 */
@Slf4j
@Configuration
@ConditionalOnClass(RedisTemplate.class)
public class RedisMessageContainerConfig {

    @Autowired
    private RedisListenerAnnotationBeanPostProcessor annotationBeanPostProcessor;
    @Autowired
    private RedisBroadCastListeneAdapter listeneAdapter;

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, ThreadPoolTaskExecutor redisEventTaskExecutor) {
        Set<String> keySet = annotationBeanPostProcessor.getBroadcastMap().keySet();
        if(!CollectionUtils.isEmpty(keySet)) {
            Set<Topic> topicSet = new HashSet<>();
            for(String key : keySet) {
                topicSet.add(new ChannelTopic(key));
            }
            RedisMessageListenerContainer container = new RedisMessageListenerContainer();
            container.setConnectionFactory(connectionFactory);
            container.addMessageListener(listeneAdapter, topicSet);
            return container;
        }
        return null;
    }

    @Bean
    @ConditionalOnMissingBean(ThreadPoolTaskExecutor.class)
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(2);
        taskExecutor.setMaxPoolSize(5);
        taskExecutor.setKeepAliveSeconds(3000);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }
}
