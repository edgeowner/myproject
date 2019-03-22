package com.hpe.modifyControl.aop;

import com.hpe.exception.AspectCheckException;
import com.hpe.modifyControl.aop.common.ModifyControlContext;
import com.hpe.modifyControl.aop.common.ObjectResult;
import com.hpe.modifyControl.aop.common.SpringContext;
import com.hpe.user.IUserTokenService;
import org.aopalliance.aop.AspectException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * 修改操作监控
 */
@Aspect//标记为切面类
@Component
@Order(2)
public class ModifyControlAspect {

    private static final Logger logger = LoggerFactory.getLogger(ModifyControlAspect.class);

    @Autowired
    private ModifyControlConfiguration configuration;

    //定义切点位置
    @Pointcut("execution(* com.hpe..*.repository.*Repository.save*(..))")
    private void repositoryCut() {
    }

    @Around("repositoryCut()")
    public Object aroudModify(ProceedingJoinPoint joinPoint) throws Throwable {

        ModifyControlContext context = configuration.getModifyControlContext(joinPoint);
        Object returnValue = null;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //在配置表里存在，并且不是系统定时任务，则进行修改时间的判断校验
        if (context.isControlEnabled() && attributes != null) {

           if(context.checkIsModify()){
               throw new AspectCheckException("此订单已经被修改，请重新刷新");
           }

            returnValue = context.setModifyDateProcess(new Date());
        }else{
            returnValue = context.process();
        }

        return returnValue;

    }


}
