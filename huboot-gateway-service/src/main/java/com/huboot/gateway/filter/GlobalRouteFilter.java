package com.huboot.gateway.filter;

import com.huboot.commons.component.auth.JwtTokenComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * http://springcloud.cn/view/349
 */
@Configuration
public class GlobalRouteFilter implements GlobalFilter {
    private Logger logger = LoggerFactory.getLogger(GlobalRouteFilter.class);
    @Autowired
    private JwtTokenComponent jwtTokenComponent;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder builder = request.mutate();
        /*String authorization = request.getHeaders().getFirst(jwtTokenComponent.getJwtProperties().HTTP_HEADER);
        if (authorization == null || !authorization.startsWith(jwtTokenComponent.getJwtProperties().TOKEN_HEAD)) {
            //如果不是用户
            logger.info("头部无凭证");
            builder.headers(httpHeaders -> {
                httpHeaders.set(jwtTokenComponent.getJwtProperties().HTTP_HEADER, jwtTokenComponent.generateTokenWithHeadForTaskService());
            });
        }*/
        return chain.filter(exchange.mutate().request(builder.build()).build());
    }
}
