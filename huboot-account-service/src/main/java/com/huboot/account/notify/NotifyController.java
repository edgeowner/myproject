package com.huboot.account.notify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class NotifyController {


    @Autowired
    private NotifyService notifyService;

    /**
     * 创建易宝分账账户回调
     *
     * @return merchantNo
     */
    @PostMapping("/notify/account/sub_account/yibao_account_callback")

    public String settlementAccountCallBack(HttpServletRequest request) throws IOException {
        String responseMsg = request.getParameter("response");
        return notifyService.response(responseMsg, NotifyService.NOTIFY_TYPE_REG);
    }

    /**
     * 下单支付回调
     *
     * @return merchantNo
     */
    @PostMapping("/notify/account/payment/order/yibao_pay_callback")
    public String payCallback(HttpServletRequest request) throws IOException {
        String responseMsg = request.getParameter("response");
        return notifyService.response(responseMsg, NotifyService.NOTIFY_TYPE_PAY);
    }

    /**
     * 退款回调通知
     **/
    @PostMapping(value = "/notify/account/payment/yibao_refund_callback")
    public String refundCallback(HttpServletRequest request) throws IOException {
        String responseMsg = request.getParameter("response");
        return notifyService.response(responseMsg, NotifyService.NOTIFY_TYPE_REFUND);
    }

    /**
     * 分账回调
     **/
    @PostMapping(value = "/notify/account/payment/yibao_divide_callBack")
    public String divideCallBack(HttpServletRequest request) throws IOException {
        String responseMsg = request.getParameter("response");
        return notifyService.response(responseMsg, NotifyService.NOTIFY_TYPE_DIVIDE);
    }

    /**
     * 提现回调
     **/
    @PostMapping(value = "/notify/account/payment/yibao_cash_withdrawal_callBack")
    public String cashWithdrawalCallBack(HttpServletRequest request) throws IOException {
        String responseMsg = request.getParameter("response");
        return notifyService.response(responseMsg, NotifyService.NOTIFY_TYPE_CASH_WITHDRAWA);
    }
}
