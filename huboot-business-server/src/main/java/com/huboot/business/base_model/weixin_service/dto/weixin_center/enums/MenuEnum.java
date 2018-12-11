package com.huboot.business.base_model.weixin_service.dto.weixin_center.enums;

/**
 * Created by Administrator on 2018/3/26 0026.
 */
public enum MenuEnum {

    home("店铺首页","view","/{0}"),
    orderlist("订单中心","view" ,"/{0}/orderlist"),
    usercenter("个人中心","view" ,"/{0}/userHome"),
    carList("车辆列表","view" ,"/{0}/Cars"),
    bigWheel("幸运大转盘", "view","/{0}/getturntable?zsActivityExtendId={1}"),
    articleRecommend("熟人推荐", "view","/{0}/home?dianPing=true"),
    miniApp("小程序", "miniprogram","pages/index/index"),
    ShuttleService("接送服务", "view","/{0}/makeRelay"),
    GoodsMall("本地特产", "view","/{0}/mallList")
    ;

    private String name;
    private String type;
    private String url;

    MenuEnum(String name,String type, String url) {
        this.name = name;
        this.type = type;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }
}
