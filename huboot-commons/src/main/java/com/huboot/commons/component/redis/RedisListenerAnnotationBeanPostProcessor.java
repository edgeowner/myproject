package com.huboot.commons.component.redis;

import com.huboot.commons.component.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/12/24 0024.
 */
@Slf4j
@Component
@ConditionalOnClass(RedisTemplate.class)
public class RedisListenerAnnotationBeanPostProcessor implements BeanPostProcessor {

    private Map<String, RedisListenerEndpoint> queueMap = new HashMap<>();

    private Map<String, Set<RedisListenerEndpoint>> broadcastMap = new HashMap<>();

    public Map<String, RedisListenerEndpoint> getQueueMap() {
        return queueMap;
    }

    public Map<String, Set<RedisListenerEndpoint>> getBroadcastMap() {
        return broadcastMap;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getDeclaredMethods();
        for(Method method : methods) {
            RedisQueueListener annotation = AnnotationUtils.findAnnotation(method, RedisQueueListener.class);
            if(annotation != null) {
                Class<?>[] clazzs = method.getParameterTypes();
                if(clazzs == null ) {
                    throw new BizException("队列 " + annotation.queue() + "监听的方法 " + method.getName() + "必须有一个参数");
                }
                if(clazzs.length != 1) {
                    throw new BizException("队列 " + annotation.queue() + "监听的方法 " + method.getName() + "只能有一个参数");
                }
                log.info("RedisQueueListener queue = " + annotation.queue());
                queueMap.put(annotation.queue(), new RedisListenerEndpoint(bean, method, clazzs[0]));
            }
            RedisBroadCastListener bcannotation = AnnotationUtils.findAnnotation(method, RedisBroadCastListener.class);
            if(bcannotation != null) {
                Class<?>[] clazzs = method.getParameterTypes();
                if(clazzs == null ) {
                    throw new BizException("广播 " + bcannotation.tpoic() + "监听的方法 " + method.getName() + "必须有一个参数");
                }
                if(clazzs.length != 1) {
                    throw new BizException("广播 " + bcannotation.tpoic() + "监听的方法 " + method.getName() + "只能有一个参数");
                }
                log.info("RedisBroadCastListener tpoic = " + bcannotation.tpoic() + ", bean=" + bean.getClass().getName() + ", method=" + method.getName());
                Set<RedisListenerEndpoint> set = broadcastMap.get(bcannotation.tpoic());
                if(CollectionUtils.isEmpty(set)) {
                    set = new HashSet<>();
                }
                set.add(new RedisListenerEndpoint(bean, method, clazzs[0]));
                broadcastMap.put(bcannotation.tpoic(), set);
            }
        }
        return bean;
    }
}
