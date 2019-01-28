package com.xiehua.notify.config;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.component.exception.ExceptionResponse;
import com.huboot.commons.utils.JsonUtil;
import com.huboot.share.common.feign.BizHystrixBadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/***
 * 说明 ：全局异常处理
 * **/
@RestControllerAdvice
public class RestExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public ExceptionResponse webExchangeBindException(WebExchangeBindException exception, HttpServletRequest request) {
        logger.error("数据绑定异常, url为：" + request.getRequestURI(), exception);
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getAllErrors().get(0).getDefaultMessage());
        return response;
    }


    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse methodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        logger.error("参数校验异常, url为：" + request.getRequestURI(), exception);
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return response;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ExceptionResponse gonstraintViolationException(ConstraintViolationException exception, HttpServletRequest request) {
        logger.error("参数校验异常, url为：" + request.getRequestURI(), exception);
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        return response;
    }


    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BizException.class)
    public ExceptionResponse excetpion(BizException exception, HttpServletRequest request) {
        logger.error("业务逻辑异常，url={{}}，GET参数={}",request.getRequestURI(),
                JsonUtil.buildNormalMapper().toJson(request.getParameterMap()), exception);
        if(!StringUtils.isEmpty(exception.getErrorKey())) {
            return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), exception.getErrorKey());
        } else {
            return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        }
    }



    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public ExceptionResponse excetpion(Exception exception, HttpServletRequest request) {
        logger.error("系统异常，url={{}}，参数={}",request.getRequestURI(),
                JsonUtil.buildNormalMapper().toJson(request.getParameterMap()), exception);
        return new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统异常");
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BizHystrixBadRequestException.class)
    public ExceptionResponse excetpion(BizHystrixBadRequestException exception, HttpServletRequest request) {
        logger.error("调用Fegin异常，url={{}}，参数={}",request.getRequestURI(),
                JsonUtil.buildNormalMapper().toJson(request.getParameterMap()), exception);
        if(!StringUtils.isEmpty(exception.getErrorKey())) {
            return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), exception.getErrorKey());
        } else {
            return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        }
    }
}
