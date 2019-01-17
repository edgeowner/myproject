package com.huboot.gateway.config;

import com.huboot.commons.component.auth.JwtTokenComponent;
import com.huboot.gateway.secruity.ApiReactiveAuthorizationManager;
import com.huboot.gateway.secruity.ApiServerAuthenticationSuccessHandler;
import com.huboot.gateway.common.CheckAuthority;
import com.huboot.gateway.secruity.ApiServerAccessDeniedHandler;
import com.huboot.gateway.secruity.ApiServerAuthenticationEntryPoint;
import com.huboot.gateway.secruity.authentication.JwtReactiveAuthenticationManager;
import com.huboot.gateway.secruity.converter.ServerJwtAuthenticationConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;

@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    private ApiReactiveAuthorizationManager apiReactiveAuthorizationManager;

    /* @Autowired
     private JwtAuthenticationProvider jwtAuthenticationProvider;*/
    @Autowired
    private JwtReactiveAuthenticationManager jwtReactiveAuthenticationManager;

    @Autowired
    private JwtTokenComponent jwtTokenComponent;
    @Autowired
    private CheckAuthority checkAuthority;

    @Bean
    public ServerAuthenticationFailureHandler serverAuthenticationFailureHandler() {
        return new ServerAuthenticationEntryPointFailureHandler(serverAuthenticationEntryPoint());
    }

    @Bean
    public ServerAuthenticationSuccessHandler serverAuthenticationSuccessHandler() {
        return new ApiServerAuthenticationSuccessHandler(jwtTokenComponent);
    }

    @Bean
    public ServerAuthenticationEntryPoint serverAuthenticationEntryPoint() {
        return new ApiServerAuthenticationEntryPoint();
    }

    /*@Bean
    public ReactiveUserDetailsService reactiveUserDetailsService() {
        return new RedisReactiveUserDetailsService();
    }*/

   /* @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        authenticationManagerBuilder.authenticationProvider(jwtAuthenticationProvider);
        return new ReactiveAuthenticationManagerAdapter(authenticationManagerBuilder.getObject());
    }*/

    @Bean
    public ServerAccessDeniedHandler serverAccessDeniedHandler() {
        return new ApiServerAccessDeniedHandler();
    }

    @Bean
    public ServerJwtAuthenticationConverter serverJwtAuthenticationConverter() {
        return new ServerJwtAuthenticationConverter(jwtTokenComponent, checkAuthority);
    }

    /**
     * 身份认证：jwt统一认证
     *
     * @return
     */
    public AuthenticationWebFilter jwtAuthenticationWebFilter() {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(jwtReactiveAuthenticationManager);
        authenticationWebFilter.setAuthenticationConverter(serverJwtAuthenticationConverter());
        authenticationWebFilter.setAuthenticationFailureHandler(serverAuthenticationFailureHandler());
        authenticationWebFilter.setAuthenticationSuccessHandler(serverAuthenticationSuccessHandler());
        return authenticationWebFilter;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        http
                .csrf().disable()
                .headers().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                .requestCache().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .anyExchange().access(apiReactiveAuthorizationManager)
                .and()
                .exceptionHandling().authenticationEntryPoint(serverAuthenticationEntryPoint())
                .accessDeniedHandler(serverAccessDeniedHandler())
                .and()
                .addFilterAt(jwtAuthenticationWebFilter(), SecurityWebFiltersOrder.HTTP_BASIC);
//        http.addFilterAt(authorizationWebFilter(), SecurityWebFiltersOrder.AUTHORIZATION);
        return http.build();
    }


}
