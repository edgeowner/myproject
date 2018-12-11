package com.huboot.business.base_model.weixin_service.dto.weixin_center.enums;

/**
 * Created by Administrator on 2018/3/28 0028.
 */
public enum WeixinFuncEnum {
    func_1(1, "消息管理权限"),
    func_2(2, "用户管理权限"),
    func_3(3, "帐号服务权限"),
    func_4(4, "网页服务权限"),
    func_5(5, "微信小店权限"),
    func_6(6, "微信多客服权限"),
    func_7(7, "群发与通知权限"),
    func_8(8, "微信卡券权限"),
    func_9(9, "微信扫一扫权限"),
    func_10(10, "微信连WIFI权限"),
    func_11(11, "素材管理权限"),
    func_12(12, "微信摇周边权限"),
    func_13(13, "微信门店权限"),
    func_14(14, "微信支付权限"),
    func_15(15, "自定义菜单权限"),
    func_16(16, "获取认证状态及信息"),
    func_17(17, "帐号管理权限（小程序）"),
    func_18(18, "开发管理与数据分析权限（小程序）"),
    func_19(19, "客服消息管理权限（小程序）"),
    func_20(20, "微信登录权限（小程序）"),
    func_21(21, "数据分析权限（小程序）"),
    func_22(22, "城市服务接口权限"),
    func_23(23, "广告管理权限"),
    func_24(24, "开放平台帐号管理权限"),
    func_25(25, "开放平台帐号管理权限（小程序）"),
    func_26(26, "微信电子发票权限"),
    ;

    private Integer value;
    private String name;

    WeixinFuncEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String valueOf(int value) {
        for (WeixinFuncEnum s : WeixinFuncEnum.values()) {
            if (s.value.equals(value))
                return s.name;
        }
        return "";
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
