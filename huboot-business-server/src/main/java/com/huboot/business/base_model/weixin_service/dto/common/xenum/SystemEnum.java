package com.huboot.business.base_model.weixin_service.dto.common.xenum;

/**
 * Created by Administrator on 2018/1/20 0020.
 */
public enum SystemEnum {

    zk(1, "直客系统"),
    miniapp(2,"微信小程序"),
    thdc(3,"同行调车"),
    miniRisk(4,"风控微信小程序");

    private Integer val;
    private String name;

    private SystemEnum(Integer val, String name) {
        this.val = val;
        this.name = name;
    }

    public Integer getVal() {
        return val;
    }
}
