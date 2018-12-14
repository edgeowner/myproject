package com.huboot.business.base_model.redisqueue;


import com.huboot.business.common.component.exception.BizException;
import com.huboot.business.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
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
    private ApplicationContext context;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /*
    使用：在一个类上加上自定义的注解：@RedisQueueContainer；
    在这个类中的对应处理方法上加上@RedisQueueListener

    @Service("promotionServiceImpl")
@RedisQueueContainer
public class PromotionServiceImpl implements IPromotionService {

    private Logger logger = LoggerFactory.getLogger(PromotionServiceImpl.class);

    @Autowired
    private IPromotionRepository promotionRepository;

    @Autowired
    private UserCacheData userCacheData;

    @Autowired
    private MiniAppFeignCilent miniAppFeignCilent;

    @Autowired
    private WxmpFeignCilent wxmpFeignCilent;

    //  -----------------redis消息队列更新累计注册人数和累计签约人数、累计粉丝数-------------------
    @RedisQueueListener(queue = RedisQueueName.DRIVER_SOURCE_COMPANY_SIGN)
    public void signedCount(String sn) {
        PromotionEntity entity = promotionRepository.findBySn(sn);
        if(entity != null){
            entity.setSignedCount(entity.getSignedCount() + 1);
            promotionRepository.modify(entity);
        }
    }

    @RedisQueueListener(queue = RedisQueueName.DRIVER_SOURCE_COMPANY)
    public void registerCount(String sn) {
        PromotionEntity entity = promotionRepository.findBySn(sn);
        if(entity != null){
            entity.setRegisterCount(entity.getRegisterCount() + 1);
            promotionRepository.modify(entity);
        }
    }

    @RedisQueueListener(queue = RedisQueueName.WXMP_SUBSCRIBE)
    public void subscribeCount(String sn) {
        PromotionEntity entity = promotionRepository.findBySn(sn);
        if(entity != null){
            entity.setSubscribeCount(entity.getSubscribeCount() + 1);
            promotionRepository.modify(entity);
        }
    }

}


使用的时候通过redisHelper.sendMessage(RedisQueueName.WXMP_SUBSCRIBE, sn.replace("qrscene_", ""));
发送消息，对应就会到名称为RedisQueueName.WXMP_SUBSCRIBE的RedisQueueListener注解的方法去处理，方法参数就是sn.replace("qrscene_", "")

这种方式的重大意义就是@RedisQueueContainer和@RedisQueueListener都是自己定义的注解

    */



    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, BeanInfo> queueMap = this.getQueueMap();
        if(!CollectionUtils.isEmpty(queueMap)) {
            ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
            taskExecutor.setCorePoolSize(5);
            taskExecutor.setMaxPoolSize(10);
            taskExecutor.setKeepAliveSeconds(3000);
            taskExecutor.afterPropertiesSet();
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

    private Map<String, BeanInfo> getQueueMap() {
        Map<String, Object> beanMap = context.getBeansWithAnnotation(RedisQueueContainer.class);
        Map<String, BeanInfo> queueMap = new HashMap<>();
        if(CollectionUtils.isEmpty(beanMap)) {
            return queueMap;
        }
        beanMap.forEach((key, value) -> {
            Method[] methods = value.getClass().getDeclaredMethods();
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
                    BeanInfo beanInfo = new BeanInfo();
                    beanInfo.setBean(value);
                    beanInfo.setMethod(method);
                    queueMap.put(annotation.queue(), beanInfo);
                }
            }
        });
        return queueMap;
    }

    private static class BeanInfo {
        private Object bean;
        private Method method;

        public Object getBean() {
            return bean;
        }

        public void setBean(Object bean) {
            this.bean = bean;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }
    }

    private class Task implements Runnable {

        private String queue;
        private String json;
        private BeanInfo bean;

        public Task(String queue, String json, BeanInfo bean) {
            this.queue = queue;
            this.json = json;
            this.bean = bean;
        }

        @Override
        public void run() {
            String message = "RedisQueueListener queue = " + queue + " 接收到消息，message = " + json;
            log.info(message);
            Class<?>[] clazzs = bean.getMethod().getParameterTypes();
            Object param0 = JsonUtil.buildNormalMapper().fromJson(json, clazzs[0]);
            try {
                bean.getMethod().invoke(bean.getBean(), param0);
            } catch (Exception e) {
                log.error("redis队列消息执行异常, " + message, e);
            }
        }
    }

}
