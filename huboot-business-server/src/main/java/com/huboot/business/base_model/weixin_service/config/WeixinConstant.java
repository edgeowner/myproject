package com.huboot.business.base_model.weixin_service.config;

import java.util.HashMap;
import java.util.Map;

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
    public final static String SET_WEAPP_SUPPORT_VERSION = "https://api.weixin.qq.com/cgi-bin/wxopen/setweappsupportversion?access_token=";
    public final static String GET_QRCODE = "https://api.weixin.qq.com/wxa/get_qrcode?access_token=";
    public final static String UNDOCODEAUDIT = "https://api.weixin.qq.com/wxa/undocodeaudit?access_token=";
    public final static String JSCODE_2_SESSION = "https://api.weixin.qq.com/sns/jscode2session?appid=${appid}&secret=${secret}&js_code=${js_code}&grant_type=authorization_code";

    //小程序审核成功
    public final static String WEAPP_AUDIT_SUCCESS = "weapp_audit_success";
    //小程序审核失败
    public final static String WEAPP_AUDIT_FAIL = "weapp_audit_fail";

    //小程序审核失败
    public final static String SUPPORT_VERSION = "1.6.4";
}
