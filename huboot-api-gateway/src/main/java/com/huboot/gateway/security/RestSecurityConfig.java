package com.huboot.gateway.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by wjc on 2016/11/3.
 */

@Configuration
@EnableWebSecurity
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .httpBasic().disable()
            .authorizeRequests()
                .antMatchers("/api-huboot/base_model/weixin_service/portal/**").permitAll()
                .antMatchers("/api-huboot/weixin_mini_app/**").permitAll()
                .antMatchers("/api-huboot/wexin_b2c_app/**").permitAll()
                .antMatchers("/api-huboot/business_manage_web/**").permitAll();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/application/**",
                "/error",
                "/favicon.ico",
                "/webjars/**"
        );
    }

}
