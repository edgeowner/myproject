package com.huboot.business.base_model.login.sso.client.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

public class ClientInfoInterceptor extends HandlerInterceptorAdapter {


    public static String IP = "ip";

    public static String SHOP_UID = "shop-uid";

    public static String CLIENT_PLATFORM = "client-platform";

    public static final String AUTO_LOGIN_TOKEN_NAME = "Auto-Token";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        RequestInfo.put(IP, getIpAddr(request));
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = (String) headerNames.nextElement();
            String value = request.getHeader(name);
            RequestInfo.put(name, value);
        }
        return true;
    }

    /**
     * 获得ip地址
     **/
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("Cdn-Src-Ip");
        if (isInvalidIp(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (isInvalidIp(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isInvalidIp(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isInvalidIp(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.length() > 15 && ip.indexOf(",") > 0) {
            ip = ip.substring(0, ip.indexOf(","));
        }
        return ip;
    }

    private boolean isInvalidIp(String ip) {
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestInfo.clear();
        //response.setHeader("Access-Control-Expose-Headers","Authorization"+","+AUTO_LOGIN_TOKEN_NAME);
    }

    public enum ClientPlatformEnum {
        zk_public, zk_mini_app;
    }


}
