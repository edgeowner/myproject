package com.huboot.business.base_model.ali_service.controller;


import com.huboot.business.base_model.ali_service.dto.SMSDTO;
import com.huboot.business.base_model.ali_service.service.ISMSSendService;
import com.huboot.business.base_model.weixin_service.dto.common.xenum.SystemEnum;
import com.huboot.business.common.utils.BigDecimalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


@Api(tags = "系统发送短信通知 API")
@RestController
@RequestMapping("/base_model/ali_service/sms")
public class SMSController{

	private final Logger logger = LoggerFactory.getLogger(SMSController.class);

	@Autowired
	private ISMSSendService sendService;

	/*使用：
	SMSDTO buyersms = new SMSDTO();
	buyersms.setNode(SMSNodeEnum.ZK_CONFIRM_ORDER);
	buyersms.setSystem(SystemEnum.zk);
	buyersms.setPhones(rentOrder.getBuyerPhone());
	buyersms.setParams(Arrays.asList(rentOrder.getSellerShopName(), BigDecimalUtil.getTwoDecimalRoundString(waitPayAmount)));
	SMSSendSao.send(buyersms);
	*/

	@PostMapping("/send")
	@ApiOperation(response = void.class, value = "创建")
	public void post(@RequestBody SMSDTO dto) throws Exception {
		sendService.send(dto);
	}


}
