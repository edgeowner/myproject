package com.huboot.account.payment.controller.wycshop;

import com.huboot.account.payment.dto.wycshop.WriteoffDTO;
import com.huboot.account.payment.service.IPaymentOrderService;
import com.huboot.share.account_service.api.dto.WriteoffPaymentCreateReqDTO;
import com.huboot.share.account_service.enums.PayTypeEnum;
import com.huboot.share.account_service.enums.PaymentOrderSourceEnum;
import com.huboot.share.account_service.enums.SubAccountTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;

@Api(tags = "网约车管理端-支付订单表 API")
@RestController
@RequestMapping(value = "/wycshop/payment/payment_order")
public class WycshopPaymentOrderController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IPaymentOrderService paymentOrderService;

	@PostMapping("/writeoff")
	@ApiOperation("核销账单")
	public void writeoff(@Validated @RequestBody WriteoffDTO writeoffDTO) {
		WriteoffPaymentCreateReqDTO reqDTO = new WriteoffPaymentCreateReqDTO();
		reqDTO.setAmount(writeoffDTO.getAmount().multiply(new BigDecimal(100)));
		reqDTO.setOfflineSeq(writeoffDTO.getOfflineSeq());
		reqDTO.setRelaId(writeoffDTO.getUserId());
		reqDTO.setRemark(writeoffDTO.getRemark());
		reqDTO.setPayType(PayTypeEnum.offline_person);
		reqDTO.setSource(PaymentOrderSourceEnum.wycdriver_writeoff);
		reqDTO.setSubAccountType(SubAccountTypeEnum.brokerage);
		paymentOrderService.writeoff(reqDTO);
	}
}
