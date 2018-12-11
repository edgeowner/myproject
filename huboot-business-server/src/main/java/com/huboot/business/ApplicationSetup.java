package com.huboot.business;

import com.huboot.business.common.config.SetupConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by Administrator on 2018/7/11 0011.
 */
public class ApplicationSetup implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent refreshedEvent) {
        ApplicationContext context = refreshedEvent.getApplicationContext();
        if(context.getParent().getParent() == null) {
            SetupConfig setupConfig =  context.getBean(SetupConfig.class);
            setupConfig.run();
        }
    }

}
