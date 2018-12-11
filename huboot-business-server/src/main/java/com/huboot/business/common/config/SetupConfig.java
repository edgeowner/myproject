package com.huboot.business.common.config;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/7/11 0011.
 */
@Component
public class SetupConfig implements Runnable {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplicationInfoManager infoManager;
    @Value("${eureka-instance.status:up}")
    private String status;


    @Override
    public void run() {
        if("down".equals(status)) {
            infoManager.setInstanceStatus(InstanceInfo.InstanceStatus.DOWN);
            logger.info("应用状态设置为DOWN");
        }
    }
}
