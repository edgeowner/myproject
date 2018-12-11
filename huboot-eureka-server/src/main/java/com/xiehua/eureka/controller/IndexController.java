package com.xiehua.eureka.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;
import com.netflix.eureka.EurekaServerContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
@Controller
public class IndexController {

    @GetMapping("/index")
    public String index(ModelMap modelMap) {
        List<InstanceInfoDTO> instanceList = new ArrayList<>();

        List<Application> applicationList = EurekaServerContextHolder.getInstance().getServerContext().getRegistry().getSortedApplications();
        if(!CollectionUtils.isEmpty(applicationList)) {
            for(Application application : applicationList) {
                List<InstanceInfo> instances = application.getInstances();
                for(int i =  0; i < instances.size(); i++) {
                    InstanceInfo instanceInfo = instances.get(i);
                    InstanceInfoDTO infoDTO = new InstanceInfoDTO();
                    if(i == 0) {
                        infoDTO.setAppName(instanceInfo.getAppName());
                        infoDTO.setRowspan(instances.size());
                    }
                    infoDTO.setInstanceId(instanceInfo.getInstanceId());
                    infoDTO.setHostName("【" + instanceInfo.getHostName()+ "】【" + instanceInfo.getIPAddr() + "】");
                    infoDTO.setPort(instanceInfo.getPort());
                    infoDTO.setStatus(instanceInfo.getStatus().name());
                    Map<String, String> metadata = instanceInfo.getMetadata();
                    infoDTO.setVersion(metadata.get("version"));
                    instanceList.add(infoDTO);
                }
            }
        }
        modelMap.put("instanceList", instanceList);

        return "index";
    }

}
