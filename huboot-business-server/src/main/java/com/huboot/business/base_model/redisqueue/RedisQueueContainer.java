package com.huboot.business.base_model.redisqueue;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2018/9/13 0013.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RedisQueueContainer {

    @AliasFor(annotation = Component.class)
    String value() default "";
}
