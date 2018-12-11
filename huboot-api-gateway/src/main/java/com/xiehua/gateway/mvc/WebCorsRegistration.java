package com.xiehua.gateway.mvc;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;

/**
 * Created by wjc on 2016/12/10.
 */
public class WebCorsRegistration extends CorsRegistration {

    public WebCorsRegistration(String pathPattern) {
        super(pathPattern);
    }

    @Override
    public CorsConfiguration getCorsConfiguration() {
        return super.getCorsConfiguration();
    }
}
