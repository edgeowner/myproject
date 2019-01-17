package com.huboot.commons.filter;

import com.huboot.commons.utils.AppAssert;
import com.huboot.commons.component.auth.JwtUser;

import java.util.HashMap;
import java.util.Map;

public class RequestInfo {

    private static ThreadLocal<Map<String, Object>> request = new ThreadLocal<Map<String, Object>>();

    public static String CLIENT_IP = "ip";
    public static String JWT_USER = "jwtUser";
    public static String CLIENT_PLATFORM = "client-platform";
    public static String CLIENT_PLATFORM_VALUE = "unknown";
    public static String ACCESS_TOKEN = "access_token";

    public static void put(String key, Object value) {
        Map<String, Object> requestContext = request.get();
        if (requestContext == null) {
            requestContext = new HashMap<String, Object>();
            request.set(requestContext);
        }
        requestContext.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> requestContext = request.get();
        return requestContext == null ? null : requestContext.get(key);
    }

    public static Map<String, Object> getAll() {
        return request.get();
    }

    public static void clearInfo() {
        Map<String, Object> requestContext = request.get();
        requestContext.clear();
    }

    public static JwtUser getJwtUser() {
        Object o = RequestInfo.get(RequestInfo.JWT_USER);
        AppAssert.notNull(o, "登陆信息不存在");
        return (JwtUser) o;
    }

    public static String getAccessToken() {
        Object o = RequestInfo.get(RequestInfo.ACCESS_TOKEN);
        if (o != null) {
            return o.toString();
        }
        return "";
    }
}
