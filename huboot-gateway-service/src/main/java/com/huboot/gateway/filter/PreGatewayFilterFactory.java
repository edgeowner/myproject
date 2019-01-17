package com.huboot.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;

public class PreGatewayFilterFactory extends AbstractGatewayFilterFactory<PreGatewayFilterFactory.Config> {
    private Logger logger = LoggerFactory.getLogger(PreGatewayFilterFactory.class);

    public PreGatewayFilterFactory() {
        super(Config.class);
    }

    public GatewayFilter apply() {
        return apply(o -> {
        });
    }

    @Override
    public GatewayFilter apply(Config config) {
        // grab configuration from Config object
        return (exchange, chain) -> {
            //If you want to build a "pre" filter you need to manipulate the
            //request before calling change.filter
            ServerHttpRequest request = exchange.getRequest();
            String requestUrl = request.getURI().getPath();
            logger.info("请求url【" + requestUrl + "】");
            //分发到各系统前，header携带用户信息
            ServerHttpRequest.Builder builder = request.mutate();
            builder.header("GatewayFilter", "PreGatewayFilterFactory success");
            //use builder to manipulate the request
            return chain.filter(exchange.mutate().request(builder.build()).build());
        };
    }

    public static class Config {
        //Put the configuration properties for your filter here
    }


}
