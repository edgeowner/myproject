package com.huboot.gateway.secruity;

import com.huboot.gateway.support.ReactiveJsonResponseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Created by Administrator on 2018/7/25 0025.
 */
public class ApiServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {

        logger.warn("用户未登录,url[{}]", exchange.getRequest().getURI().getPath());

        return ReactiveJsonResponseWriter.response(exchange.getResponse())
                .status(HttpStatus.UNAUTHORIZED)
                .message("用户未登录").print();

    }
}
