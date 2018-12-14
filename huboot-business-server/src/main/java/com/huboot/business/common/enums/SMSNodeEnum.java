package com.huboot.business.common.enums;

/**
 * Created by Administrator on 2018/1/20 0020.
 */
public enum SMSNodeEnum {

    REGISTER_VERIFICATION_CODE(1,
            "短信验证码{0}，10分钟内有效，请勿泄露，并注意及时完成注册操作。"
    ),
    ZK_CONFIRM_ORDER(2,
            "{0}已确认了你的订单，待付金额：{1}元，请尽快付款，未付款订单将在24小时后自动取消。"
    ),
    ZK_CREATE_ORDER(3,
            "你有新的直客订单待接单，请至订单中心处理，订单编号为：{0}"
    ),
    ZK_ORDER_FRIST_PAY_SUCCESS(4,
            "你收到了新的订单款项，请至订单中心查看，付款金额为{0}元，订单编号为{1}。"
    ),
    ZK_ORDER_BUYER_CANCEL(5,
            "你有订单已被客户取消，订单金额为{0}，订单编号为{1}。"
    ),
    ZK_ORDER_APPLY_CONTINUE_RENT(6,
            "你有订单续租申请待处理，请至订单中心查看，订单编号为{0}。"
    ),
    ZK_ORDER_CONTINUE_RENT_PAY_SUCCESS(7,
            "你收到了新的订单续租款项，请至订单中心查看，付款金额为{0}，订单编号为{1}。"
    ),
    ZK_ORDER_FINAL_PAY_SUCCESS(8,
            "你有订单已完成结算，请至订单中心查看，付款金额为{0}，订单编号为{1}。"
    ),
    ZK_SHUTTLE_CREATE_ORDER(9,
            "你有新的接送订单待确认，请至订单中心处理，订单编号为{0}"
    ),
    ZK_SHUTTLE_CONFIRM_ORDER(10,
            "{0}已确认了你的订单，待付金额：{1}元，请尽快付款，未付款订单将在24小时后自动取消"
    ),
    ZK_SHUTTLE_FRIST_PAY_SUCCESS(11,
            "你收到了新的订单款项，请至订单中心查看，付款金额为{0}元，订单编号为{1}"
    ),
    ZK_SHUTTLE_SEND_CAR(12,
            "你的接送服务订单{0}商家已安排为您服务，请至订单中心查看，祝您旅途愉快！"
    ),
    ZK_SHUTTLE_ORDER_CANCEL(13,
            "你有订单已被客户取消，订单金额为{0}，订单编号为{1}。"
    ),
    ZK_SHUTTLE_ORDER_CLOSE(14,
            "您的接送服务订单{0}，已被商家取消，期待下次为您服务！"
    ),
    //商城
    ZK_MALL_ORDER_NOTIFY_TO_PAY(15,
            "你已在{0}成功下单，待付金额：{1}元，请24小时内完成付款，若已支付请忽略。"
    ),
    ZK_MALL_ORDER_SHIP_ONLINE(16,
            "你在{0}的购物订单已发货，交易方式为在线交易，请跟进物流动态等待签收吧。"
    ),
    ZK_MALL_ORDER_SHIP_FACE(17,
            "你在{0}的购物订单已发货，交易方式为当面交易，请至指定交易点提取订单商品。"
    ),
    ZK_MALL_ORDER_CANCEL(18,
            "你有一笔零售商品订单已被取消，订单编号为{0}，请注意查看。"
    ),
    ZK_MALL_ORDER_CLOSE(19,
            "你在{0}的购物订单已被取消，退款金额为{1}，订单编号为{2}，请注意查看。"
    ),
    ZK_RETURNCAR_WAIT_PAY(20,
            "你在{0}的租车订单已确认还车，请至订单中心支付尾款，付款金额为{1}，若已支付请忽略。"
    ),

    //短信营销
    ZK_PROMOTION_100(100,
            "嗨，老朋友，好久不见，{0}贴心为您奉上 {1} 元优惠券，愿您由此开启愉快的租车之旅！领取请猛戳: {2} ,退订回TD"
    ),

    THDC_MINI_RISK_LOGIN(1000, "短信验证码为{0}，10分钟内有效，请勿泄露，并注意及时完成登录操作。"),

    //退违章押金查询回复
    REFUND_VIOLATION_DEPOSIT_REPLY(21,
            "{0}，已回复你的违章押金查询，退款{1}，详情请关注商家公众号，请戳{2}"
    ),
    //续租提醒
    ZK_RELET_REMIND(22,
            "你在{0}的租用的车辆将在1天后还车，如需续租请至订单中心申请，若已续租请忽略。"
    );

    private Integer node;
    private String content;

    SMSNodeEnum(Integer node, String content) {
        this.node = node;
        this.content = content;
    }

    public static SMSNodeEnum valueOf(int node) {
        for (SMSNodeEnum s : SMSNodeEnum.values()) {
            if (s.node.equals(node))
                return s;
        }
        throw new IllegalArgumentException(String.format("值%s不能转换成枚举", node));
    }

    public Integer getNode() {
        return node;
    }

    public String getContent() {
        return content;
    }

}
