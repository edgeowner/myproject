package com.huboot.file;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringCloudApplication()
@EnableFeignClients(basePackages = {"com.huboot.share"})
@ComponentScan(basePackages = {"com.huboot"})
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }

}
