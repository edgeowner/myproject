package com.huboot.business.base_model.orderhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/7/4 0004.
 */
@Component
public class HandlerCompleteEventPublisherConfig {

    @Autowired
    private ThreadPoolTaskExecutor orderAsync;

    public ThreadPoolTaskExecutor getOrderAsync() {
        return orderAsync;
    }
}
