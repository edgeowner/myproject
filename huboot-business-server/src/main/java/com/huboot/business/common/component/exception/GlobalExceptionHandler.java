package com.huboot.business.common.component.exception;

import com.huboot.business.base_model.login.sso.client.exception.RemoteInvokException;
import com.huboot.business.base_model.login.sso.client.exception.SsoException;
import com.huboot.business.base_model.login.sso.client.exception.UnAuthorizationException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Map;
import org.apache.commons.collections.map.HashedMap;

/***
 * 说明 ：全局异常处理
 * **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger =  LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public ExceptionResponse webExchangeBindException(WebExchangeBindException exception, HttpServletRequest request) {
        logger.error("数据绑定异常, url为：" + request.getRequestURI(),exception);
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getAllErrors().get(0).getDefaultMessage());
        return response;
    }


    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse methodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request){
        logger.error("参数校验异常, url为：" + request.getRequestURI(),exception);
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return response;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ExceptionResponse gonstraintViolationException(ConstraintViolationException exception, HttpServletRequest request){
        logger.error("参数校验异常, url为：" + request.getRequestURI(),exception);
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        return response;
    }


    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BizException.class)
    public ExceptionResponse webExchangeBindException(BizException exception, HttpServletRequest request) {
        logger.error("业务异常, url为：" + request.getRequestURI(),exception);
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        response.setCode(exception.getCode());
        response.setBizData(exception.getBizData());
        return response;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AddServiceBizException.class)
    public Map<String,Object> webExchangeBindException(AddServiceBizException exception, HttpServletRequest request) {
        logger.error("业务异常, url为：" + request.getRequestURI(),exception);
        Map<String,Object> map = new HashedMap();
        map.put("code",exception.getCode());
        map.put("message",exception.getMsg());
        map.put("bizData",exception.getBizData());
        return map;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RemoteInvokException.class)
    public ExceptionResponse webExchangeBindException(RemoteInvokException exception, HttpServletRequest request) {
        logger.error("远程服务调用异常, url为：" + request.getRequestURI(),exception);
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        response.setCode(exception.getCode());
        response.setBizData(exception.getBizData());
        return response;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ExceptionResponse webExchangeBindException(MissingServletRequestParameterException exception, HttpServletRequest request) {
        logger.error("请求路径参数缺失异常, url为：" + request.getRequestURI(),exception);
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        response.setCode("400");
        return response;
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthorizationException.class)
    public ExceptionResponse webExchangeBindException(UnAuthorizationException exception, HttpServletRequest request) {
        logger.error("未经授权的访问, url为：" + request.getRequestURI(),exception);
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        response.setCode(exception.getCode());
        return response;
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(SsoException.class)
    public ExceptionResponse webExchangeBindException(SsoException exception, HttpServletRequest request) {
        logger.error("sso授权失败, url为：" + request.getRequestURI(),exception);
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        response.setCode(exception.getCode());
        return response;
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenBizException.class)
    public ExceptionResponse forbiddenBizException(ForbiddenBizException exception, HttpServletRequest request) {
        logger.error("您的权限不够, url为：" + request.getRequestURI(),exception);
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        response.setCode(exception.getCode());
        return response;
    }


    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ExceptionResponse webExchangeBindException(Exception exception, HttpServletRequest request) {
        logger.error("服务器开小差, url为：" + request.getRequestURI(),exception);
        ExceptionResponse response = new ExceptionResponse();
        response.setCode("500");
        response.setMessage("服务器开小差");
        return response;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FeignException.class)
    public ExceptionResponse feignExceptionException(FeignException exception, HttpServletRequest request) {
        logger.error("服务器开小差, url为：" + request.getRequestURI(),exception);
        ExceptionResponse response = new ExceptionResponse();
        response.setCode("500");
        String message = exception.getMessage();
        String content = message.split("content:\n")[1];
        try {
            response.setMessage(content);
        } catch (Exception e1) {
            return response;
        }
        return response;
    }
}
