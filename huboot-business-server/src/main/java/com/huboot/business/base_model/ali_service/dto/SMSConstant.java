package com.huboot.business.base_model.ali_service.dto;

/**
 * Created with IntelliJ IDEA.
 * User: jerry.wang
 * Date: 2016-08-03
 * Time: 18:12
 * 短信相关常量
 */
public class SMSConstant {

    //注册时短信验证码
    public static final String SMS_REGISTER_CODE = "sms_register_code:%s";
    //注册有效时间单位分钟
    public static final String SMS_REGISTER_EXPIRY = "10";

    //保存单个手机号每天发送的次数
    public static final String SMS_REGISTER_NUM = "sms_register_num:%s";
    //允许单个手机号每天发送的次数
    public static final Integer SMS_REGISTER_MAX_NUM = 6;
    //允许单个手机号发送的提示
    public static final Integer SMS_REGISTER_PROMPT_NUM = 3;

}
