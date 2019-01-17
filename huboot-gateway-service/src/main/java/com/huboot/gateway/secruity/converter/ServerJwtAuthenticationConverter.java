package com.huboot.gateway.secruity.converter;

import com.huboot.commons.component.auth.JwtTokenComponent;
import com.huboot.commons.sso.SSOConstant;
import com.huboot.gateway.common.CheckAuthority;

import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.ServerHttpBasicAuthenticationConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class ServerJwtAuthenticationConverter extends ServerHttpBasicAuthenticationConverter {

    private JwtTokenComponent jwtTokenComponent;
    private CheckAuthority checkAuthority;

    public ServerJwtAuthenticationConverter(JwtTokenComponent jwtTokenComponent, CheckAuthority checkAuthority) {
        this.jwtTokenComponent = jwtTokenComponent;
        this.checkAuthority = checkAuthority;
    }

    @Override
    public Mono<Authentication> apply(ServerWebExchange exchange) {
        //1、从http头部取出token,缺省是匿名用户
        //2、交给用于登陆的UsernamePasswordAuthenticationToken --处理：JwtReactiveAuthenticationManager
        ServerHttpRequest request = exchange.getRequest();
        String authorization = request.getHeaders().getFirst(jwtTokenComponent.getJwtProperties().HTTP_HEADER);
        String shopSn = request.getHeaders().getFirst(SSOConstant.SHOP_SN_HEADER);
        if (authorization == null || !authorization.startsWith(jwtTokenComponent.getJwtProperties().TOKEN_HEAD)) {
            authorization = jwtTokenComponent.generateTokenWithHeadForAnonymous(shopSn);
            exchange.getAttributes().put(jwtTokenComponent.getJwtProperties().HTTP_HEADER, authorization);
        }
        String principal = null;
        if (StringUtils.hasText(shopSn)) {
            principal = shopSn;
        }
        authorization = authorization.replace(jwtTokenComponent.getJwtProperties().TOKEN_HEAD, "");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, authorization);
        //解决一个坑：前端不管token失效状态，判断如果不是匿名权限，需要检查jwtClaims的有效期
        String requestUrl = request.getURI().getPath();
        HttpMethod httpMethod = request.getMethod();
        token.setDetails(checkAuthority.check(String.valueOf(SSOConstant.ANONYMOUS_API_ROLE_ID), requestUrl, httpMethod));
        return Mono.just(token);
    }

}
