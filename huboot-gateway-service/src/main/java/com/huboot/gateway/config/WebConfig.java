package com.huboot.gateway.config;

import com.huboot.commons.component.auth.JwtTokenComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.CorsRegistration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * Created by Administrator on 2018/7/24 0024.
 */
@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

    @Autowired
    private JwtTokenComponent jwtTokenComponent;

    @Bean
    @Order(Integer.MIN_VALUE)
    public CorsWebFilter corsWebFilter() {
        WebCorsRegistration corsRegistration = new WebCorsRegistration("/**");
        corsRegistration
                .allowedOrigins(CorsConfiguration.ALL)
                .allowedMethods(CorsConfiguration.ALL)
                .allowedHeaders(CorsConfiguration.ALL)
                .exposedHeaders(jwtTokenComponent.getJwtProperties().HTTP_HEADER);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", corsRegistration.getCorsConfiguration());
        return new CorsWebFilter(source);
    }

    private static class WebCorsRegistration extends CorsRegistration {

        public WebCorsRegistration(String pathPattern) {
            super(pathPattern);
        }

        @Override
        protected CorsConfiguration getCorsConfiguration() {
            return super.getCorsConfiguration();
        }
    }
}
