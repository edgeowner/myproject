package com.huboot.user.common.constant;

/**
 * Created with IntelliJ IDEA.
 * User: jerry.wang
 * Date: 2016-08-03
 * Time: 18:12
 * 微信相关常量
 */
public class WeixinConstant {

    //ACCESS_TOKEN_CACHE
    public static final String WEIXIN_SCOPE_BASE = "snsapi_base";
    public static final String WEIXIN_SCOPE_USERINFO = "snsapi_userinfo";
    public static final String WEIXIN_STATE = "state_xiehua";

    public final static String FRIST = "first";
    public final static String KEYWORD1 = "keyword1";
    public final static String KEYWORD2 = "keyword2";
    public final static String KEYWORD3 = "keyword3";
    public final static String KEYWORD4 = "keyword4";
    public final static String KEYWORD5 = "keyword5";
    public final static String KEYWORD6 = "keyword6";
    public final static String KEYWORD7 = "keyword7";
    public final static String KEYWORD8 = "keyword8";
    public final static String KEYWORD9 = "keyword9";
    public final static String REMARK = "remark";



    public final static String COLOR_FRIST = "#000000";
    public final static String COLOR_KEYWORD = "#003366";
    public final static String COLOR_REMARK = "#003366";

    public static String KEYWORD(int i) {
        switch (i) {
            case 0: return KEYWORD1;
            case 1: return KEYWORD2;
            case 2: return KEYWORD3;
            case 3: return KEYWORD4;
            case 4: return KEYWORD5;
            case 5: return KEYWORD6;
            case 6: return KEYWORD7;
            case 7: return KEYWORD8;
            case 8: return KEYWORD9;
            default: throw new RuntimeException("keywords已超");
        }
    }


    //小程序
    public final static String COMMIT = "https://api.weixin.qq.com/wxa/commit?access_token=";
    public final static String SUBMIT_AUDIT = "https://api.weixin.qq.com/wxa/submit_audit?access_token=";
    public final static String MODIFY_DOMAIN = "https://api.weixin.qq.com/wxa/modify_domain?access_token=";
    public final static String WEBVIEW_DOMAIN = "https://api.weixin.qq.com/wxa/setwebviewdomain?access_token=";
    public final static String RELEASE = "https://api.weixin.qq.com/wxa/release?access_token=";
    public final static String GET_AUDITSTATUS = "https://api.weixin.qq.com/wxa/get_auditstatus?access_token=";
    public final static String GET_LAST_AUDITSTATUS = "https://api.weixin.qq.com/wxa/get_latest_auditstatus?access_token=";
    public final static String SET_WEAPP_SUPPORT_VERSION = "https://api.weixin.qq.com/cgi-bin/wxopen/setweappsupportversion?access_token=";
    public final static String GET_QRCODE = "https://api.weixin.qq.com/wxa/get_qrcode?access_token=";
    public final static String UNDOCODEAUDIT = "https://api.weixin.qq.com/wxa/undocodeaudit?access_token=";

    //小程序审核成功
    public final static String WEAPP_AUDIT_SUCCESS = "weapp_audit_success";
    //小程序审核失败
    public final static String WEAPP_AUDIT_FAIL = "weapp_audit_fail";

    //小程序默认基础库版本
    public static final String DEFAULT_VERSION = "1.6.4";

    //创建虚拟开放平台
    public final static String OPEN_CREATE = "https://api.weixin.qq.com/cgi-bin/open/create?access_token=";
    //将公众号/小程序绑定到开放平台帐号下
    public final static String OPEN_BIND = "https://api.weixin.qq.com/cgi-bin/open/bind?access_token=";
    //将公众号/小程序从开放平台帐号下解绑
    public final static String OPEN_UNBIND = "https://api.weixin.qq.com/cgi-bin/open/unbind?access_token=";
    //获取公众号/小程序所绑定的开放平台帐号
    public final static String GET_OPEN_APPID = "https://api.weixin.qq.com/cgi-bin/open/get?access_token=";
}
