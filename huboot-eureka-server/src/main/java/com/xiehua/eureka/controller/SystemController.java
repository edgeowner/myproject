package com.xiehua.eureka.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;
import com.netflix.eureka.EurekaServerContextHolder;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
@RestController
public class SystemController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/system/getApplicationList")
    @ApiOperation(response = String.class, value = "获取服务列表")
    public List<Application> getApplicationList() {
        List<Application> applicationList = EurekaServerContextHolder.getInstance().getServerContext().getRegistry().getSortedApplications();
        return applicationList;
    }

    @PostMapping("/system/setApplicationStatus")
    @ApiOperation(response = String.class, value = "设置服务状态")
    public String setApplicationStatus(@RequestBody StatusDTO statusDTO) {
        List<Application> applicationList = this.getApplicationList();
        InstanceInfo instanceInfo = null;
        for(Application application : applicationList) {
            instanceInfo = application.getByInstanceId(statusDTO.getInstanceId());
            if(instanceInfo != null) {
                break;
            }
        }
        if(instanceInfo == null) {
            throw new RuntimeException("实例没找到");
        }
        String status = "";
        if(statusDTO.getStatus() == 1) {
            status = InstanceInfo.InstanceStatus.UP.name().toLowerCase();
        } else {
            status = InstanceInfo.InstanceStatus.DOWN.name().toLowerCase();
        }

        String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/actuator/service-registry";

        logger.info("发送设置状态请求：" + url);

        URI uri = URI.create(url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        Map<String, String> body = new HashMap<>();
        body.put("status", status);
        HttpEntity<Map> entity = new HttpEntity<>(body, headers);
        restTemplate.postForEntity(uri, entity, String.class);

        logger.info(statusDTO.getInstanceId() + " 设置 " + status);
        return "ok";
    }


}
