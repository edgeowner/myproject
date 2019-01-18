package com.huboot.user.weixin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "wechat.miniapp")
public class WechatMiniappProperties {

    //网约车开发小程序
    private Miniapp wycdevelop;

    //网约车开发小程序
    private Miniapp zkshop;

    //网约车开发小程序
    private Miniapp zkuser;

    @Data
    public static class Miniapp {
        //
        private String appid;
        //
        private String secret;
    }

    public List<Miniapp> getMiniappList() {
        List<Miniapp> list = new ArrayList<>();
        list.add(wycdevelop);
        list.add(zkshop);
        list.add(zkuser);
        return list;
    }

}
