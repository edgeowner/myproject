package com.huboot.commons.component.redis;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2018/9/13 0013.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisQueueListener {

    /**
     * 队列
     * @return
     */
    String queue();
}
