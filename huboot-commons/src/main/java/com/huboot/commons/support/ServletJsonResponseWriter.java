package com.huboot.commons.support;

import com.huboot.commons.component.exception.ExceptionResponse;
import com.huboot.commons.utils.JsonUtil;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2018/6/28 0028.
 */
public class ServletJsonResponseWriter {

    private HttpServletResponse response;

    private ExceptionResponse exceptionResponse = new ExceptionResponse();

    public static ServletJsonResponseWriter response(HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        ServletJsonResponseWriter writer = new ServletJsonResponseWriter();
        writer.setResponse(response);
        return writer;
    }

    private ServletJsonResponseWriter setResponse(HttpServletResponse response) {
        this.response = response;
        return this;
    }

    public ServletJsonResponseWriter status(HttpStatus httpStatus) {
        response.setStatus(httpStatus.value());
        exceptionResponse.setCode(httpStatus.value());
        return this;
    }

    public ServletJsonResponseWriter message(String message) {
        exceptionResponse.setMessage(message);
        return this;
    }

    public void print() throws IOException {
        PrintWriter writer = response.getWriter();
        writer.print(JsonUtil.buildNormalMapper().toJson(exceptionResponse));
        writer.flush();
    }
}
