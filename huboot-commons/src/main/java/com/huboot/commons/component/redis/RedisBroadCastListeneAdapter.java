package com.huboot.commons.component.redis;

import com.huboot.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * Created by Administrator on 2019/1/9 0009.
 */
@Slf4j
@Component
@ConditionalOnClass(RedisTemplate.class)
public class RedisBroadCastListeneAdapter implements MessageListener {

    @Autowired
    private RedisListenerAnnotationBeanPostProcessor annotationBeanPostProcessor;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    private StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();


    @Override
    public void onMessage(Message message, @Nullable byte[] pattern) {
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();
        String topic = stringRedisSerializer.deserialize(channel);
        String json = stringRedisSerializer.deserialize(body);
        log.info("RedisBroadCastListener topic = " + topic + " 接收到消息，message = " + json);
        Set<RedisListenerEndpoint> endpointSet = annotationBeanPostProcessor.getBroadcastMap().get(topic);
        if(!CollectionUtils.isEmpty(endpointSet)) {
            for(RedisListenerEndpoint endpoint : endpointSet) {
                taskExecutor.execute(new Task(json, endpoint));
            }
        }
    }

    private class Task implements Runnable {

        private String json;
        private RedisListenerEndpoint endpoint;

        public Task(String json, RedisListenerEndpoint endpoint) {
            this.json = json;
            this.endpoint = endpoint;
        }

        @Override
        public void run() {
            Object param = JsonUtil.buildNormalMapper().fromJson(json, endpoint.getMethodClazz());
            try {
                endpoint.getMethod().invoke(endpoint.getBean(), param);
            } catch (Exception e) {
                log.error("redis广播消息执行异常, " + endpoint, e);
            }
        }
    }
}
