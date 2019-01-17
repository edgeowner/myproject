package com.huboot.share.common.constant;

public class BizConstant {

    public static final Long CHINA_AREA_ROOT_ID = 0L;//中国区根ID

    //司机其他渠道二级渠道
    public static final String DRIVER_SECOND_LEVEL_SOURCE_FOR_OTHER = "S0000000000000000000";

    //SSOConstant 见：10000000
    //微服务API
    public static final Long INNER_API_ROLE_ID = 20000000L;
    //内管端API
    public static final Long ADMIN_API_ROLE_ID = 30000000L;
    //网约车店铺管理端的权限(API和菜单)
    public static final Long WYCSHOP_ROLE_ID = 40000000L;
    //网约车店铺司机端的权限(API和菜单)
    public static final Long WYCDRIVERMINIAPP_ROLE_ID = 50000000L;

    //超级管理员的权限-菜单
    public static final String WYCSHOP_MENU_ROLE_NAME = "超级管理员";

    //2019版直客用户端的权限(API和菜单)
    public static final Long ZKUSER_ROLE_ID = 70000000L;

    //2019版直客商家端的权限(API和菜单)-初始化权限：未认证
    public static final Long ZKSHOP_INIT_ROLE_ID = 60000000L;
    //2019版直客商家端的权限(API和菜单)-店长
    public static final Long ZKSHOP_MANAGER_ROLE_ID = 60000001L;
    public static final String ZKSHOP_MANAGER_ROLE_NAME = "店长";
    //2019版直客商家端的权限(API和菜单)-财务
    public static final Long ZKSHOP_FINANCE_ROLE_ID = 60000002L;
    //2019版直客商家端的权限(API和菜单)-司机
    public static final Long ZKSHOP_DRIVER_ROLE_ID = 60000003L;
}
