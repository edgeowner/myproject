package com.huboot.commons.component.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

@Component
public class CustomErrorHandler extends DefaultResponseErrorHandler {

    private final Logger logger = LoggerFactory.getLogger(CustomErrorHandler.class);

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        /*logger.error("远程服务调用出错httpCode:{}",response.getStatusCode());
        String body = IOUtils.toString(response.getBody(),StandardCharsets.UTF_8.name());
        logger.error("远程服务调用出错httpBody:{}",body);
        if(response.getStatusCode().equals(HttpStatus.BAD_REQUEST) || response.getStatusCode().equals(HttpStatus.UNAUTHORIZED) || response.getStatusCode().equals(HttpStatus.FORBIDDEN)){
            try{
                RemoteInvokException exception = mapper.readValue(body,RemoteInvokException.class);
                throw exception;
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            throw new RuntimeException("服务器开小差");
        }*/
    }

}