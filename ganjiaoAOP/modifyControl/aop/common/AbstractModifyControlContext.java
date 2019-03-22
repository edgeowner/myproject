package com.hpe.modifyControl.aop.common;

import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Description:
 *
 * @author hqr
 * @version 1.0
 */
public abstract class AbstractModifyControlContext implements ModifyControlContext {

    private static final Logger logger = LoggerFactory.getLogger(AbstractModifyControlContext.class);


    protected JoinPoint joinPoint;
    protected MethodInvocation methodInvocation;

    protected void init() {
        if (joinPoint != null) {
            methodInvocation = getMethodInvocation();
        }
    }


    @Override
    public Method getMethod() {
        return methodInvocation.getMethod();
    }

    private MethodInvocation getMethodInvocation() {
        if (methodInvocation == null) {
            Class<? extends JoinPoint> pointClazz = joinPoint.getClass();
            try {
                Field methodInvocationField = pointClazz.getDeclaredField("methodInvocation");
                methodInvocationField.setAccessible(true);
                methodInvocation = (MethodInvocation) methodInvocationField.get(joinPoint);
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                logger.error("Get MethodInvocation object failed.", e);
            }
        }
        return methodInvocation;
    }

    @Override
    public void setJoinPoint(JoinPoint joinPoint) {
        this.joinPoint = joinPoint;
        init();
    }

    @Override
    public MethodChecker getMethodChecker() {
        return new DefaultMethodChecker();
    }

    private class DefaultMethodChecker implements MethodChecker {
        @Override
        public boolean isSuccess() {
            return true;
        }
    }

}
