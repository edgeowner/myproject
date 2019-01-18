package com.huboot.user.common.config;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

@Configuration
@EnableSwagger2Doc
public class SwaggerConfig {
    /**
     * 为了网关调用，设置docket的pathMapping
     *
     * @param dockets
     */
    public SwaggerConfig(List<Docket> dockets) {
        dockets.forEach(docket -> docket.pathMapping("/user-service"));
    }

}
