package com.huboot.share.common.constant;



import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/30 0030.
 */
public class PayConfigConstant {


    /*public static Map<PayTypeEnum, PayPlatformEnum> PAYMAP = new HashMap<>();

    static {
        //微信
        PAYMAP.put(PayTypeEnum.wxpay_jsapi, PayPlatformEnum.weixin);
        PAYMAP.put(PayTypeEnum.wxpay_native, PayPlatformEnum.weixin);
        PAYMAP.put(PayTypeEnum.wxpay_app, PayPlatformEnum.weixin);
        PAYMAP.put(PayTypeEnum.wxpay_lite, PayPlatformEnum.weixin);
        //支付宝
        PAYMAP.put(PayTypeEnum.alipay_wap, PayPlatformEnum.alipay);
        //中国银联
        PAYMAP.put(PayTypeEnum.union_agentpay, PayPlatformEnum.china_union);
        //线下
        PAYMAP.put(PayTypeEnum.offline_person, PayPlatformEnum.offline);
        PAYMAP.put(PayTypeEnum.offline_system, PayPlatformEnum.offline);
    }*/

    /**
     * 支付信息扩展key
     */
    //优惠金额key
    public static final String DISCOUNT = "discount";
    //线下支付流水号key
    public static final String OFFLINESEQ = "offlineSeq";
}
