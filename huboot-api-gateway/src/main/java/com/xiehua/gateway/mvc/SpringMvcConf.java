package com.xiehua.gateway.mvc;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Created by Administrator on 2016/11/24 0024.
 */
@Configuration
public class SpringMvcConf implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        // 对响应头进行CORS授权
        WebCorsRegistration corsRegistration = new WebCorsRegistration("/**");
        this._configCorsParams(corsRegistration);
        // 注册CORS过滤器
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", corsRegistration.getCorsConfiguration());
        CorsFilter corsFilter = new CorsFilter(configurationSource);
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(corsFilter);
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegistrationBean;
    }

    private void _configCorsParams(CorsRegistration corsRegistration) {
        corsRegistration.allowedOrigins(CorsConfiguration.ALL)
                .allowedMethods(HttpMethod.PATCH.name(), HttpMethod.HEAD.name(), HttpMethod.OPTIONS.name(), HttpMethod.GET.name(), HttpMethod.HEAD.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name())
                .allowedHeaders(CorsConfiguration.ALL);
//                .exposedHeaders(HttpHeaders.SET_COOKIE);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        this._configCorsParams(registry.addMapping("/**"));
        //X-Requested-With,X-HTTP-Method-Override,origin, Content-Type, accept, Authorization,authName,secretKey,applicationCode, SourceUrl, src_url_base, X_Requested_With
    }
    /**
     * 配置跨域
     *
     * @param registry
     */
    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("*//**")
                .allowedOrigins(CorsConfiguration.ALL)
                .allowCredentials(false)
                .allowedHeaders(CorsConfiguration.ALL)
                .allowedMethods(
                        HttpMethod.PATCH.name(),
                        HttpMethod.HEAD.name(),
                        HttpMethod.OPTIONS.name(),
                        HttpMethod.GET.name(),
                        HttpMethod.HEAD.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name());
    }*/


}