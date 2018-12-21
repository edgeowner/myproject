package com.huboot.business.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Swagger配置类:
 * @author Mr.zhang
 * @version v.0.1
 * @date 2018年01月11日
 */

@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix="swagger",name="activate",matchIfMissing=false)
public class SwaggerConfig {


    private class XHApiInfo extends ApiInfo {

        /**
         * @param title 模块
         * @param contact 负责人
         */
        public XHApiInfo(String title, String contact) {
            this(title, "", "", "", new Contact(contact, "", ""), "", "", new ArrayList());
        }

        public XHApiInfo(String title, String description, String version, String termsOfServiceUrl, Contact contact, String license, String licenseUrl, Collection<VendorExtension> vendorExtensions) {
            super(title, description, version, termsOfServiceUrl, contact, license, licenseUrl, vendorExtensions);
        }
    }

   /* @Bean
    public Docket framework(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xiehua.framework"))
                .build()
                .groupName("framework")
                .apiInfo((ApiInfo)new XHApiInfo("-framework模块相关接口", "XXX"));
        return docket;
    }*/

    @Bean
    public Docket base_model(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.huboot.business.base_model"))
                .build()
                .groupName("base_model")
                .apiInfo((ApiInfo)new XHApiInfo("base_model模块相关接口", "XXX"));
        return docket;
    }

    /*@Bean
    public Docket business(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xiehua.business"))
                .build()
                .groupName("business")
                .apiInfo((ApiInfo)new XHApiInfo("-business模块相关接口", "XXX"));
        return docket;
    }

    @Bean
    public Docket web_app(){
       Docket docket = new Docket(DocumentationType.SWAGGER_2)
               .genericModelSubstitutes(DeferredResult.class)
               .useDefaultResponseMessages(false)
               .forCodeGeneration(true)
               .pathMapping("/")
               .select()
               .apis(RequestHandlerSelectors.basePackage("com.xiehua.business.rest_model.web_app"))
               .build()
               .groupName("web_app")
               .apiInfo((ApiInfo)new XHApiInfo("web_app模块相关接口", "张怡君"));
       return docket;
    }



    @Bean
    public Docket weixin_b2b_app(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xiehua.business.rest_model.weixin_b2b_app"))
                .build()
                .groupName("weixin_b2b_app")
                .apiInfo((ApiInfo)new XHApiInfo("weixin_b2b_app模块相关接口", "XXX"));
        return docket;
    }


    @Bean
    public Docket weixin_mini_app(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xiehua.business.rest_model.weixin_mini_app"))
                .build()
                .groupName("weixin_mini_app")
                .apiInfo((ApiInfo)new XHApiInfo("weixin_mini_app模块相关接口", "XXX"));
        return docket;
    }

    @Bean
    public Docket wexin_b2c_app(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xiehua.business.rest_model.wexin_b2c_app"))
                .build()
                .groupName("wexin_b2c_app")
                .apiInfo((ApiInfo)new XHApiInfo("wexin_b2c_app模块相关接口", "XXX"));
        return docket;
    }

    @Bean
    public Docket business_manage_web(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xiehua.business.rest_model.business_manage_web"))
                .build()
                .groupName("business_manage_web")
                .apiInfo((ApiInfo)new XHApiInfo("business_manage_web模块相关接口", "XXX"));
        return docket;
    }

    @Bean
    public Docket promotion_web() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xiehua.business.rest_model.promotion"))
                .build()
                .groupName("promotion_web")
                .apiInfo((ApiInfo) new XHApiInfo("promotion模块相关接口", "满口蛀牙"));
        return docket;
    }

    @Bean
    public Docket shopping_car() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xiehua.business.rest_model.shopping_car"))
                .build()
                .groupName("shopping_cart")
                .apiInfo((ApiInfo) new XHApiInfo("购物车模块相关接口", "满口蛀牙"));
        return docket;
    }


    @Bean
    public Docket e_card(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xiehua.business.rest_model.e_card"))
                .build()
                .groupName("e_card")
                .apiInfo((ApiInfo)new XHApiInfo("e_card模块相关接口", "XXX"));
        return docket;
    }

    @Bean
    public Docket html2img() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xiehua.business.rest_model.html2img"))
                .build()
                .groupName("html2img")
                .apiInfo((ApiInfo) new XHApiInfo("html2img模块相关接口", "满口蛀牙"));
        return docket;
    }*/

}