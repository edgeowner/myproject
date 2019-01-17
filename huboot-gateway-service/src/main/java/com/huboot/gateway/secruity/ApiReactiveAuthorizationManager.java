package com.huboot.gateway.secruity;

import com.huboot.gateway.common.CheckAuthority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ApiReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CheckAuthority checkAuthority;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext context) {
        ServerHttpRequest request = context.getExchange().getRequest();
        String requestUrl = request.getURI().getPath();
        HttpMethod httpMethod = request.getMethod();
        return authentication
                .filter(a -> a.isAuthenticated())
                .flatMapIterable(a -> a.getAuthorities())
                .any(authority -> checkAuthority.check(authority.getAuthority(), requestUrl, httpMethod))
                .map(hasAuthority -> new AuthorizationDecision(hasAuthority))
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

}