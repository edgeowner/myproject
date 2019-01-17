package com.huboot.commons.component.redis;

import com.huboot.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/9/13 0013.
 */
@Slf4j
@Component
@ConditionalOnClass(RedisTemplate.class)
public class RedisQueueListenContainer implements InitializingBean {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisListenerAnnotationBeanPostProcessor annotationBeanPostProcessor;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;



    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, RedisListenerEndpoint> queueMap = annotationBeanPostProcessor.getQueueMap();
        if(!CollectionUtils.isEmpty(queueMap)) {
            new Thread(() -> {
                while (true) {
                    queueMap.forEach((key, value) -> {
                        String json = redisTemplate.boundListOps(key).rightPop(500, TimeUnit.MILLISECONDS);
                        if(!StringUtils.isEmpty(json)) {
                            taskExecutor.execute(new Task(key, json, value));
                        }
                    });
                }
            }).start();
        }
    }

    private class Task implements Runnable {

        private String queue;
        private String json;
        private RedisListenerEndpoint endpoint;

        public Task(String queue, String json, RedisListenerEndpoint endpoint) {
            this.queue = queue;
            this.json = json;
            this.endpoint = endpoint;
        }

        @Override
        public void run() {
            String message = "RedisQueueListener queue = " + queue + " 接收到消息，message = " + json;
            log.info(message);
            Object param = JsonUtil.buildNormalMapper().fromJson(json, endpoint.getMethodClazz());
            try {
                endpoint.getMethod().invoke(endpoint.getBean(), param);
            } catch (Exception e) {
                log.error("redis队列消息执行异常, " + message, e);
            }
        }
    }

}
