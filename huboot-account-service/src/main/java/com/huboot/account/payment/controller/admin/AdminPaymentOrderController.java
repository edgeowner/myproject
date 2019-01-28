package com.huboot.account.payment.controller.admin;

import com.huboot.account.payment.service.IPaymentOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

@Api(tags = "内管端-支付订单表 API")
@RestController
@RequestMapping(value = "/admin/payment/payment_order")
public class AdminPaymentOrderController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IPaymentOrderService paymentOrderService;


}
