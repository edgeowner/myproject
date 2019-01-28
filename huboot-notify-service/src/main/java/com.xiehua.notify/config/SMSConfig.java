package com.xiehua.notify.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Administrator on 2018/1/17 0017.
 */
@Data
@ConfigurationProperties("xiehua.sms")
public class SMSConfig {

    // 地址
    private String url = "http://www.dh3t.com";
    //账号
    private String account1;
    //密码
    private String password1;
    //营销账号
    private String account2;
    //营销密码
    private String password2;
    //是否开启
    private boolean open = false;
    //重试次数
    private int retryCount = 1;
    //
    private String subCode = "";

}
