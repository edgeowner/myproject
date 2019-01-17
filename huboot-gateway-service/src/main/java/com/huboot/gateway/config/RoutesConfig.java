package com.huboot.gateway.config;

import com.huboot.gateway.filter.PostGatewayFilterFactory;
import com.huboot.gateway.hystrix.HystrixCommandController;
import com.huboot.gateway.filter.PreGatewayFilterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.factory.HystrixGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.RetryGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.function.Consumer;

@Configuration
public class RoutesConfig {

    @Autowired
    private KeyResolver addressKeyResolver;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                //权重路由
                // .route(r->r.weight("provide",9).and().path("/test/weight").filters(f -> f.retry(3).filter(new PreGatewayFilterFactory().apply()).filter(new PostGatewayFilterFactory().apply()).requestRateLimiter().rateLimiter(RedisRateLimiter.class,
                // config -> config.setBurstCapacity(1).setReplenishRate(1)).configure(config -> config.setKeyResolver(addressKeyResolver)).stripPrefix(1).hystrix(config -> config.setFallbackUri("forward:/fallback").setName("fallbackcmd"))).uri("http://localhost:3001/test/weight"))
                // .route(r->r.weight("provide",5).and().path("/test/weight").filters(f -> f.retry(3).filter(new PreGatewayFilterFactory().apply()).filter(new PostGatewayFilterFactory().apply()).requestRateLimiter().rateLimiter(RedisRateLimiter.class,
                // config -> config.setBurstCapacity(1).setReplenishRate(1)).configure(config -> config.setKeyResolver(addressKeyResolver)).stripPrefix(1).hystrix(config -> config.setFallbackUri("forward:/fallback").setName("fallbackcmd"))).uri("http://localhost:3002/test/weight"))
                //集成了可能会用到的各种filter http://springcloud.cn/view/349
                .route(r -> r.path("/user-service/**")
                        .filters(f -> f.retry(retryConsumer)
                                .filter(new PreGatewayFilterFactory().apply())
                                .filter(new PostGatewayFilterFactory().apply())
                                .requestRateLimiter().rateLimiter(RedisRateLimiter.class, config -> config.setBurstCapacity(200).setReplenishRate(200)).configure(config -> config.setKeyResolver(addressKeyResolver))
                                .stripPrefix(1)
                                .hystrix(configConsumer))
                        .uri("lb://user-service"))
                .route(r -> r.path("/file-service/**")
                        .filters(f -> f.retry(retryConsumer)
                                .filter(new PreGatewayFilterFactory().apply())
                                .filter(new PostGatewayFilterFactory().apply())
                                .requestRateLimiter().rateLimiter(RedisRateLimiter.class, config -> config.setBurstCapacity(200).setReplenishRate(200)).configure(config -> config.setKeyResolver(addressKeyResolver))
                                .stripPrefix(1)
                                .hystrix(configConsumer)
                        )
                        .uri("lb://file-service"))
                .route(r -> r.path("/marketing-service/**")
                        .filters(f -> f.retry(retryConsumer)
                                .filter(new PreGatewayFilterFactory().apply())
                                .filter(new PostGatewayFilterFactory().apply())
                                .requestRateLimiter().rateLimiter(RedisRateLimiter.class, config -> config.setBurstCapacity(200).setReplenishRate(200)).configure(config -> config.setKeyResolver(addressKeyResolver))
                                .stripPrefix(1)
                                .hystrix(configConsumer)
                        )
                        .uri("lb://marketing-service"))
                .route(r -> r.path("/account-service/**")
                        .filters(f -> f.retry(retryConsumer)
                                .filter(new PreGatewayFilterFactory().apply())
                                .filter(new PostGatewayFilterFactory().apply())
                                .requestRateLimiter().rateLimiter(RedisRateLimiter.class, config -> config.setBurstCapacity(200).setReplenishRate(200)).configure(config -> config.setKeyResolver(addressKeyResolver))
                                .stripPrefix(1)
                                .hystrix(configConsumer)
                        )
                        .uri("lb://account-service"))
                .route(r -> r.path("/riskcontrol-service/**")
                        .filters(f -> f.retry(retryConsumer)
                                .filter(new PreGatewayFilterFactory().apply())
                                .filter(new PostGatewayFilterFactory().apply())
                                .requestRateLimiter().rateLimiter(RedisRateLimiter.class, config -> config.setBurstCapacity(200).setReplenishRate(200)).configure(config -> config.setKeyResolver(addressKeyResolver))
                                .stripPrefix(1)
                                .hystrix(configConsumer)
                        )
                        .uri("lb://riskcontrol-service"))
                .route(r -> r.path("/capitalfund-service/**")
                        .filters(f -> f.retry(retryConsumer)
                                .filter(new PreGatewayFilterFactory().apply())
                                .filter(new PostGatewayFilterFactory().apply())
                                .requestRateLimiter().rateLimiter(RedisRateLimiter.class, config -> config.setBurstCapacity(200).setReplenishRate(200)).configure(config -> config.setKeyResolver(addressKeyResolver))
                                .stripPrefix(1)
                                .hystrix(configConsumer)
                        )
                        .uri("lb://capitalfund-service"))
                .route(r -> r.path("/trade-service/**")
                        .filters(f -> f.retry(retryConsumer)
                                .filter(new PreGatewayFilterFactory().apply())
                                .filter(new PostGatewayFilterFactory().apply())
                                .requestRateLimiter().rateLimiter(RedisRateLimiter.class, config -> config.setBurstCapacity(200).setReplenishRate(200)).configure(config -> config.setKeyResolver(addressKeyResolver))
                                .stripPrefix(1)
                                .hystrix(configConsumer)
                        )
                        .uri("lb://trade-service"))
                .route(r -> r.path("/trade-service/**")
                        .filters(f -> f.retry(retryConsumer)
                                .filter(new PreGatewayFilterFactory().apply())
                                .filter(new PostGatewayFilterFactory().apply())
                                .requestRateLimiter().rateLimiter(RedisRateLimiter.class, config -> config.setBurstCapacity(200).setReplenishRate(200)).configure(config -> config.setKeyResolver(addressKeyResolver))
                                .stripPrefix(1)
                                .hystrix(configConsumer)
                        )
                        .uri("lb:wss://trade-service"))
                .route(r -> r.path("/trade-service/**")
                        .filters(f -> f.retry(retryConsumer)
                                .filter(new PreGatewayFilterFactory().apply())
                                .filter(new PostGatewayFilterFactory().apply())
                                .requestRateLimiter().rateLimiter(RedisRateLimiter.class, config -> config.setBurstCapacity(200).setReplenishRate(200)).configure(config -> config.setKeyResolver(addressKeyResolver))
                                .stripPrefix(1)
                                .hystrix(configConsumer)
                        )
                        .uri("lb:ws://trade-service"))
                .build();
    }

    private Consumer<RetryGatewayFilterFactory.RetryConfig> retryConsumer = retryConfig -> {
        retryConfig.setMethods(HttpMethod.GET)
                .setStatuses(HttpStatus.NOT_FOUND, HttpStatus.BAD_GATEWAY)
                .setRetries(1);
    };
    private Consumer<HystrixGatewayFilterFactory.Config> configConsumer = config -> {
        config.setFallbackUri("forward:/" + HystrixCommandController.API_FALLBACK);
    };
}
