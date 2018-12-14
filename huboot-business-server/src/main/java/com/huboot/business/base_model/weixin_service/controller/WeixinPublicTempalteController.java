package com.huboot.business.base_model.weixin_service.controller;

import com.huboot.business.base_model.weixin_service.dto.common.xenum.SystemEnum;
import com.huboot.business.base_model.weixin_service.dto.common.xenum.WeiXinNode;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.ZKWeixinMessageDTO;
import com.huboot.business.base_model.weixin_service.service.IWeixinPublicTempalteService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Arrays;

@Api(tags = "微信设置-公众号微信模板信息表 API")
@RestController
@RequestMapping(value = "/base_model/weixin_service/weixinPublicTempalte")
public class WeixinPublicTempalteController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IWeixinPublicTempalteService weixinPublicTempalteService;

	/*使用：
	WeixinMessageDTO buyerMessage = new WeixinMessageDTO();
	buyerMessage.setUserGid(rentOrder.getBuyerGid());
	buyerMessage.setWeixinUid(rentOrderNotifyService.getWeixinUid(rentOrder.getSellerShopId()));
	buyerMessage.setNode(WeiXinNode.Node.violation_to_user.getValue());
	buyerMessage.setFrist("您在"+rentOrder.getSellerShopName()+"期间发生了违章，查看详情~（若已查看请忽略）");
	buyerMessage.addKeyword(rentOrder.getProductCarLicense());
	buyerMessage.addKeyword(String.valueOf(orderViolationSearchRedisDTO.getViolationCount()));
	buyerMessage.setRemark("订单编号:"+orderSn);
	buyerMessage.setUrlParmasList(Arrays.asList(rentOrder.getSellerShopId(), orderSn));
	buyerSendMessageSao.sendZKWeixinMessage(buyerMessage);
     */

	@PostMapping(value = "/sendZKWeixinMessage")
	@ApiOperation(response = void.class, value = "发送直客微信通知")
	public void sendZKWeixinMessage(@RequestBody ZKWeixinMessageDTO dto) throws Exception {
		weixinPublicTempalteService.sendZKWeixinMessage(dto);
	}

	@PostMapping(value = "/initPublicTempalte/{weixinUid}")
	@ApiOperation(response = void.class, value = "初始化微信消息模板")
	public void initPublicTempalte(@PathVariable("weixinUid") String  weixinUid) throws Exception {
		weixinPublicTempalteService.initPublicTemplate(weixinUid, SystemEnum.zk.getVal());
	}

}
