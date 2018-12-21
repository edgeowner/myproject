package com.huboot.business.base_model.ali_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Administrator on 2018/1/17 0017.
 */
@ConfigurationProperties("huboot.sms")
public class SMSConfigBean {

    // 地址
    private String url = "http://www.dh3t.com";
    // 短信签名（必填）
    private String sign = "【盒子】";
    //账号
    private String account;
    //密码
    private String password;
    //是否开启
    private boolean open = false;
    //重试次数
    private int retryCount = 1;
    //
    private String subCode = "";

    //营销账号
    private String account2;
    //营销密码
    private String password2;

    public String getAccount2() {
        return account2;
    }

    public void setAccount2(String account2) {
        this.account2 = account2;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }
}
