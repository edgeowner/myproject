package com.huboot.gateway.secruity.authentication;

import com.huboot.commons.component.auth.JwtClaims;
import com.huboot.commons.component.auth.JwtTokenComponent;
import com.huboot.commons.component.auth.JwtUser;
import com.huboot.commons.sso.SSOConstant;
import com.huboot.commons.sso.SSORedisHashName;
import com.huboot.gateway.secruity.JwtUserDetailsChecker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {
    @Autowired
    private JwtTokenComponent jwtTokenComponent;
    @Autowired
    private JwtUserDetailsChecker jwtUserDetailsChecker;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        //1、从UsernamePasswordAuthenticationToken中获取jwt的token信息，进行解析
        String details = (String) authentication.getCredentials();
        // JwtClaims jwtClaims = jwtTokenComponent.decodeAndVerify(details);
        JwtClaims jwtClaims = jwtTokenComponent.decodeAndVerifySignature(details);
        //比对头部的shop-sn
        if (StringUtils.hasText((String) authentication.getPrincipal())) {
            if (!authentication.getPrincipal().equals(jwtClaims.getUser().getShopSn())) {
                return Mono.error(new BadCredentialsException("Invalid Credentials"));
            }
        }
        //访问的资源不是匿名用户的权限，检查jwtClaims的有效期，如果过期返回401
        if (!(Boolean) authentication.getDetails()) {
            //用户是否有凭证
            if (jwtClaims.isUser() && jwtClaims.isExpired()) {
                return Mono.error(new BadCredentialsException("Invalid Credentials"));
            } else if (jwtClaims.isAnonymous()) {
                return Mono.error(new BadCredentialsException("Invalid Credentials"));
            }
        }
        List<String> roles = new ArrayList<>();
        //这里需要sub为user的claims;
        JwtUser jwtUser = jwtClaims.getUser();
        if (jwtClaims.isUser()) {
            //2、从redis获取用户的role信息,默认有个系统预留的开放角色
            roles.add(String.valueOf(SSOConstant.ANONYMOUS_API_ROLE_ID));
            List<String> userPermission = (List<String>) redisTemplate.opsForHash().get(SSORedisHashName.USER_PERMISSION, String.valueOf(jwtUser.getUserId()));
            if (!CollectionUtils.isEmpty(userPermission)) {
                roles.addAll(userPermission);
            }
        } else if (jwtClaims.isAnonymous()) {
            //匿名用户
            roles.add(String.valueOf(SSOConstant.ANONYMOUS_API_ROLE_ID));
        }
        List<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        jwtUser.setAuthorities(authorities);
//        jwtUserDetailsChecker.check(jwtUser);
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(jwtUser.getUsername(), null, authorities);
        result.setDetails(jwtClaims);
        return Mono.just(result);
    }
}
