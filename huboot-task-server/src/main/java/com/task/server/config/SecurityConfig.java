package com.task.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Created by xlaoy on 2016/11/3.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private static final String SYSTEM_API_ACCESS_ROLE = "TASK_API";

    @Value("${api.username}")
    private String username;
    @Value("${api.password}")
    private String password;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .passwordEncoder(new SimplePasswordEncoder())
            .withUser(username)
            .password(password)
            .roles(SYSTEM_API_ACCESS_ROLE);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .httpBasic().and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            .anyRequest().hasRole(SYSTEM_API_ACCESS_ROLE);
    }

    private class SimplePasswordEncoder implements PasswordEncoder {

        @Override
        public String encode(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override
        public boolean matches(CharSequence charSequence, String s) {
            return charSequence.equals(s);
        }
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
            "/task_server/**",
            "/actuator/**",
            "/error",
            "/favicon.ico"
        );
    }
}
