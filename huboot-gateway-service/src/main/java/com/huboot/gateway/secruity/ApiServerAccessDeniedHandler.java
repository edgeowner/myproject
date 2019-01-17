package com.huboot.gateway.secruity;

import com.huboot.gateway.support.ReactiveJsonResponseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Created by Administrator on 2018/8/1 0001.
 */
public class ApiServerAccessDeniedHandler implements ServerAccessDeniedHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        logger.warn("用户权限不足,url[{}]", exchange.getRequest().getURI().getPath());

        return ReactiveJsonResponseWriter.response(exchange.getResponse())
                .status(HttpStatus.FORBIDDEN)
                .message("用户权限不足").print();
    }
}
