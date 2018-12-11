package com.huboot.business.base_model.weixin_service.dto.common.xenum;

/**
 * Created by Administrator on 2018/1/31 0031.
 */
public class WeiXinNode {

    public static enum ZKNode {
        submit_order(1, "下单通知"),
        confirm_order(2, "确认订单通知（提醒支付）"),
        confirm_take_car(3, "确认提车通知"),
        confirm_return_car(4, "确认还车通知"),
        cancel_order(5, "订单取消"),
        continue_rent_handled(6, "申请续租已处理通知"),
        to_return_car(7, "还车提醒(还车前)"),
        to_take_car(8, "提车提醒(提车前)"),
        last_pay(9, "尾款支付提醒"),
        deposit_refund(10, "押金退还提醒"),
        shuttle_submit_order(11, "接送机下单成功提醒"),
        shuttle_confirm_order(12, "接送机订单确认提醒"),
        shuttle_send_car(13, "派车通知"),
        shuttle_confirm_send_car(14, "确认派车通知"),
        shuttle_cancel_order(15, "接送机订单取消通知"),
        //直客商城
        mall_notify_pay(16, "待付款通知"),
        mall_order_ship(17, "发货通知"),
        mall_order_receipt(18, "收货通知"),
        mall_order_cancel(19, "订单取消"),
        mall_order_close(20, "订单关闭"),
        //退违章押金查询回复
        refund_violation_deposit_reply(21, "退违章押金查询回复"),
        //违章查询
        violation_to_user(22,"有违章用户通知"),
        //续租提醒
        relet_remid_to_user(23, "续租提醒")

        ;
        private Integer value;
        private String node;




        ZKNode(Integer value, String node) {
            this.value = value;
            this.node = node;
        }

        public Integer getValue() {
            return value;
        }
    }

    public static enum THDCNode {
        zk_create_order(201, "直客下单通知"),
        zk_pay_success(202, "直客支付完成通知"),//首款，续租款，尾款
        zk_order_cancel(203, "直客订单取消通知"),
        zk_continue_rent(204, "直客申请续租通知"),
        zk_take_car_advance(205, "直客提车提醒通知"),//提车前
        zk_return_car_advance(206, "直客还车提醒通知"),//还车前
        refund_violation_deposit(207, "退违章押金提醒"),//还车前

        zk_shuttle_order_create(208, "接送机新订单提醒"),
        zk_shuttle_pay_success(209, "接送机订单付款通知"),
        zk_shuttle_send_car(210, "派车通知"),
        zk_shuttle_order_cancel(211, "接送机订单取消通知"),

        zk_model_share_apply_advance(301, "车型共享申请通知"),//还车前
        zk_model_share_agree_advance(302, "车型共享同意通知"),//还车前
        zk_model_share_refuse_advance(303, "车型共享拒绝通知"),//还车前

        zk_model_status_update(304, "修改车辆状态通知"),//还车前

        zk_mall_order_create(350, "下单通知"),//下单通知
        zk_mall_order_pay_success(351, "付款成功"),//付款成功
        zk_mall_order_cancel(352, "订单取消"),//订单取消

        //违章商户通知
        violation_to_shop(400,"有违章商户通知"),
        not_violation_to_user(401,"未违章商户通知"),
        thirty_violation_unprocessed(402,"三十天未退押金提醒"),


        //退违章查询提交通知
        refund_violation_commit_nofity(500,"退违章查询提交通知")
        ;

        private Integer value;
        private String node;

        THDCNode(Integer value, String node) {
            this.value = value;
            this.node = node;
        }

        public Integer getValue() {
            return value;
        }
    }
}
