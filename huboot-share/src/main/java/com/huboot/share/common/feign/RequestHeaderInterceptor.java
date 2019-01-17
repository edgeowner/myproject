package com.huboot.share.common.feign;


import com.huboot.commons.component.auth.JwtTokenComponent;
import com.huboot.commons.filter.RequestInfo;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.encoding.BaseRequestInterceptor;
import org.springframework.cloud.openfeign.encoding.FeignClientEncodingProperties;
import org.springframework.util.StringUtils;

/**
 * Created by Administrator on 2018/2/23 0023.
 */

public class RequestHeaderInterceptor extends BaseRequestInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JwtTokenComponent jwtTokenComponent;

    public void setJwtTokenComponent(JwtTokenComponent jwtTokenComponent) {
        this.jwtTokenComponent = jwtTokenComponent;
    }

    public RequestHeaderInterceptor(FeignClientEncodingProperties properties) {
        super(properties);
    }

    /**
     * 为了能够获取header信息，hystrix隔离采用SEMAPHORE（信号量）隔离方法，
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        String accessToken = RequestInfo.getAccessToken();
        if(StringUtils.isEmpty(RequestInfo.getAccessToken())) {
            accessToken = jwtTokenComponent.generateTokenWithHeadForAnonymous("");
        }
        //template.header(JwtProperties.HTTP_HEADER, accessToken);
    }
}
