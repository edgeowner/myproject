package com.huboot.share.account_service.api.dto.yibao.pay;

import lombok.Data;

import java.io.Serializable;

@Data
public class YiBaoPayRespDTO implements Serializable {

    private String parentMerchantNo;//	主商户 编号	String	Max(11)	系 统商或者平台商商编，如果是单商户，和收单商户商编保持一致	10012426723
    private String merchantNo;//	商户 编号	String	Max(11)	收单商户商编	10012426723
    private String orderId;//	商户 订单号	String	Max(11)	商户订单号	merchant12345
    private String uniqueOrderNo;//	易宝流水号	String	Max(28)	★易宝内部生成唯一订单流水号， 请妥善保存，后续查询订单及订单退款都会用到。	1001201612070000000000000565
    private String status;//	订单状态	String	Max(16)	订单状态： 订单成功	SUCCESS
    private String orderAmount;//	订单金额	String	Max(10)	订单金额， 下单请求金额	88.88
    private String payAmount;//	实付金额	String	Max(10)	用户实际支付订单金额	88.88
    private String requestDate;//	请求时间	String	Max(19)	商户请求下单时间	2017-12-12 13:23:45
    private String paySuccessDate;//	完成时间	String	Max(19)	支付完成时间	2017-12-12 13:23:45
    /***
     * NCPAY(“一键支付”)
     *     SCCANPAY(“用户扫码”)
     *     MSCANPAY(“商家扫码”)
     *     EANK(“网银支付”)
     *     EWALLET(“钱包支付”)
     *     EWALLETH5(“钱包H5支付”)
     *     CFL(“分期支付”)
     *     WECHAT_OPENID(“微信公众号”)
     *     POS(“POS支付”)
     *     ZFB_SHH(“支付宝生活号”)
     *     BK_ZF(“绑卡支付”)
     *     ZF_ZHZF(“商户账户支付”)
     *     YHKFQ_ZF(“银行卡分期”)
     *     GRHYZF(“会员账户支付”)	SCCANPAY
     *     private String platformType;//	平台分类	String	Max(50)	枚举值：
     *     WECHAT 微信
     *     ALIPAY 支付宝
     *     NET 网银
     *     NCPAY 快捷
     *     CFL 分期（马上金融）	NCPAY
     * ******/
    private String paymentProduct;//	支付产品	String	Max(50)	枚举值：
    private String openID;//	公众号用 户openid	String	Max(200)
    private String unionID;//

}
