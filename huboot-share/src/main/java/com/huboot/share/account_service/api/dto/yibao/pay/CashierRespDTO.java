package com.huboot.share.account_service.api.dto.yibao.pay;

import lombok.Data;

import java.io.Serializable;

@Data
public class CashierRespDTO implements Serializable {

    private String code;//	String	接口返回码
    private String message;//	String	返回信息
    private String payTool;//	String	入参回传
    private String payType;//	String	入参回传
    private String token;//	String	入参回传
    private String merchantNo;//	String	请求接口的商户编号
    /**
     * 当payTool为SCCANPAY时，返回”url”；
     *     当payTool为WECHAT_OPENID时，返回”url”或”json”；
     *     当payTool为MSCANPAY时，返回空；
     *     当payTool为ZFB_SHH时，返回’json’；
     *     当payTool为EWALLET时，返回’json’
     * **/
    private String resultType;//	String	返回数据类型，用于调用方判断如何处理返回结果。

    private String resultData;//	String	公众号或生活号或SDK时，返回json，用json里面的数据调用微信或支付宝的支付接口

    private String extParamMap;//	String	JSON格式，商户扫码场景下，此参数返回是否需商户输入支付密码，若返回需要输入支付密码，表示支付尚未完成，请商户密切关注易宝的支付异步通知，支付是否成功，以异步通知为准。


}
