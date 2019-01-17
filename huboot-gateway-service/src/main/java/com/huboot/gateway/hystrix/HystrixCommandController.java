package com.huboot.gateway.hystrix;

import com.huboot.commons.component.exception.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

/**
 * Created by Administrator on 2018/8/3 0003.
 */
@RestController
public class HystrixCommandController {

    public static final String API_FALLBACK = "gateway_hystrix_api_fallback";

    @RequestMapping("/" + API_FALLBACK)
    public ExceptionResponse fallback(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器开小差了");
    }

}
