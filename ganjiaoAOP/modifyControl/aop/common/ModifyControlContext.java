package com.hpe.modifyControl.aop.common;

import com.hpe.log.aop.common.api.OperationLog;
import org.aspectj.lang.JoinPoint;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * Description: 定义切面的上下文信息
 *
 * @author hqr
 * @version 1.0
 */
public interface ModifyControlContext {

    Object process();

    Method getMethod();

    MethodChecker getMethodChecker();
    interface MethodChecker {
        boolean isSuccess();

    }

    void setJoinPoint(JoinPoint joinPoint);

    boolean isControlEnabled();

    boolean checkIsModify();

    Object setModifyDateProcess(Date date);

}
