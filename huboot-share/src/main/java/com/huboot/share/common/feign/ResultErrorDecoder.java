package com.huboot.share.common.feign;


import com.huboot.commons.component.exception.ExceptionResponse;
import com.huboot.commons.utils.JsonUtil;
import feign.FeignException;
import feign.Request;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Created by Administrator on 2018/3/13 0013.
 */
public class ResultErrorDecoder implements ErrorDecoder {

    private Logger logger = LoggerFactory.getLogger(ResultErrorDecoder.class);

    private static final String SPLIT = "; content:\n";

    /**
     * 对于restful抛出的4xx的错误，也许大部分是业务异常，并不是服务提供方的异常
     * 因此在进行feign client调用的时候，需要进行errorDecoder去处理，
     * 适配为HystrixBadRequestException，好避开circuit breaker的统计，否则就容易误判
     * @param methodKey
     * @param response
     * @return
     */
    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException feignException = FeignException.errorStatus(methodKey, response);
        Request request = response.request();
        logger.error("FeignException异常，url={{}}, exception=[{}]", request.url(), feignException.getMessage());
        if(response.status() >= 400 && response.status() <= 500){
            ExceptionResponse exceptionResponse = new ExceptionResponse(response.status(), "系统异常");
            String message = feignException.getMessage();
            if(!StringUtils.isEmpty(message)) {
                String json = message.split(SPLIT)[1];
                exceptionResponse = JsonUtil.buildNormalMapper().fromJson(json, ExceptionResponse.class);
            }
            if(!StringUtils.isEmpty(exceptionResponse.getErrorKey())) {
                return new BizHystrixBadRequestException(exceptionResponse.getErrorKey(), exceptionResponse.getMessage());
            } else {
                return new BizHystrixBadRequestException(exceptionResponse.getMessage());
            }
        }
        return feignException;
    }
}
