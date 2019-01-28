package com.huboot.account;

import com.task.client.config.EnableTaskClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableTaskClient
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.xiehua.share"})
@SpringBootApplication(scanBasePackages = {"com.xiehua"})
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}
