package com.xiehua.notify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Administrator on 2018/8/28 0028.
 */
@Controller
public class ViewController {

    @GetMapping("/admin/smslog")
    public String smslog() {
        return "smslog";
    }
}
