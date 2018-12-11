package com.huboot.business.base_model.login.sso.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig implements Serializable{

    private String header;

    private String secret;

    private String expiration;

    private String tokenHead;

    private List<PermitUrl> permit;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getTokenHead() {
        return tokenHead;
    }

    public void setTokenHead(String tokenHead) {
        this.tokenHead = tokenHead;
    }

    public List<PermitUrl> getPermit() {
        return permit;
    }

    public void setPermit(List<PermitUrl> permit) {
        this.permit = permit;
    }
}
