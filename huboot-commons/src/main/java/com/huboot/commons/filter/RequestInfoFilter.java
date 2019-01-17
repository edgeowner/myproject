package com.huboot.commons.filter;


import com.huboot.commons.component.auth.JwtTokenComponent;
import com.huboot.commons.utils.NetworkUtil;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;


/**
 * token的校验
 * 该类继承自OncePerRequestFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 *
 * @author wangjianchun on 2018/08/27.
 */
public class RequestInfoFilter extends OncePerRequestFilter {

    private JwtTokenComponent jwtTokenComponent;

    public RequestInfoFilter(JwtTokenComponent jwtTokenComponent) {
        this.jwtTokenComponent = jwtTokenComponent;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RequestInfo.put(RequestInfo.CLIENT_IP, NetworkUtil.getIpAddress(request));
        RequestInfo.put(RequestInfo.CLIENT_PLATFORM, RequestInfo.CLIENT_PLATFORM_VALUE);
        //根据url,设置platform
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = (String) headerNames.nextElement();
            String value = request.getHeader(name);
            RequestInfo.put(name, value);
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            RequestInfo.clearInfo();
        }
    }
}
