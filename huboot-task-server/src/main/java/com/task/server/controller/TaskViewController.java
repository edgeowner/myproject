package com.task.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Administrator on 2018/8/19 0019.
 */
@Controller
public class TaskViewController {

    @GetMapping("/admin/index")
    public String index() {
        return "index";
    }

    @GetMapping("/admin/delay")
    public String delay() {
        return "delay";
    }

    @GetMapping("/admin/log")
    public String log(@RequestParam(value = "taskId", required = false)String taskId, ModelMap modelMap) {
        modelMap.put("taskId", taskId);
        return "log";
    }

    @GetMapping("/admin/blackip")
    public String blackip() {
        return "blackip";
    }

}
