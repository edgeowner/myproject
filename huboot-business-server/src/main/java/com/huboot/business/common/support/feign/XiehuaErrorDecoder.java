package com.huboot.business.common.support.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huboot.business.base_model.login.sso.client.exception.RemoteInvokException;
import com.huboot.business.common.component.exception.BizException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class XiehuaErrorDecoder implements ErrorDecoder {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final ErrorDecoder delegate = new ErrorDecoder.Default();

    @Autowired
    @Qualifier("objectMapper")
    private ObjectMapper mapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        // check error code
        if (response.status() == 400)  {
            try{
                throw mapper.readValue(response.body().asInputStream(), BizException.class);
            }catch (IOException e){
                logger.error("远程调用错误:",response.body());
                throw new RemoteInvokException("远程调用错误");
            }
        }
        return delegate.decode(methodKey, response);
    }
}
