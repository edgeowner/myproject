package com.huboot.gateway.secruity;


import com.huboot.commons.component.auth.JwtClaims;
import com.huboot.commons.component.auth.JwtTokenComponent;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class ApiServerAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    private JwtTokenComponent jwtTokenComponent;

    public ApiServerAuthenticationSuccessHandler(JwtTokenComponent jwtTokenComponent) {
        this.jwtTokenComponent = jwtTokenComponent;
    }

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder builder = request.mutate();
        JwtClaims jwtClaims = (JwtClaims) authentication.getDetails();
        if (jwtClaims.isUser()) {
            //折半处理
            String newToken = jwtTokenComponent.refreshTokenExp(jwtClaims);
            //从头部取出
            if (StringUtils.isEmpty(newToken)) {
                newToken = webFilterExchange.getExchange().getRequest().getHeaders().getFirst(jwtTokenComponent.getJwtProperties().HTTP_HEADER);
                newToken = newToken.replace(jwtTokenComponent.getJwtProperties().TOKEN_HEAD, "");
            }
            exchange.getResponse().getHeaders().set(jwtTokenComponent.getJwtProperties().HTTP_HEADER, jwtTokenComponent.generateTokenWithHead(newToken));
        }else if(jwtClaims.isAnonymous()){
            builder.headers(httpHeaders -> {
                httpHeaders.set(jwtTokenComponent.getJwtProperties().HTTP_HEADER, exchange.getAttribute(jwtTokenComponent.getJwtProperties().HTTP_HEADER));
            });
            exchange.getAttributes().remove(jwtTokenComponent.getJwtProperties().HTTP_HEADER);
        }
        return webFilterExchange.getChain().filter(exchange.mutate().request(builder.build()).build());
    }
}
