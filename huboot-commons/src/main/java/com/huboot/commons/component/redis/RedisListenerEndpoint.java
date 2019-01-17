package com.huboot.commons.component.redis;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/12/24 0024.
 */
public class RedisListenerEndpoint {

    private Object bean;
    private Method method;
    private Class<?> methodClazz;

    public RedisListenerEndpoint() {
    }

    public RedisListenerEndpoint(Object bean, Method method, Class<?> methodClazz) {
        this.bean = bean;
        this.method = method;
        this.methodClazz = methodClazz;
    }

    public Class<?> getMethodClazz() {
        return methodClazz;
    }

    public void setMethodClazz(Class<?> methodClazz) {
        this.methodClazz = methodClazz;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "【bean=" + bean.getClass().getName() + ",method=" + method.getName() + "】";
    }
}
