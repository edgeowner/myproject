package com.huboot.business.base_model.login.sso.client.config;

import java.io.Serializable;

public class PermitUrl implements Serializable{

    private String url;

    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PermitUrl() {

    }

    public PermitUrl(String url, String name) {
        this.url = url;
        this.name = name;
    }
}
