package com.huboot.business.base_model.weixin_service.controller;

import com.huboot.business.base_model.weixin_service.dto.common.xenum.SystemEnum;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.ZKWeixinMessageDTO;
import com.huboot.business.base_model.weixin_service.service.IWeixinPublicTempalteService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "微信设置-公众号微信模板信息表 API")
@RestController
@RequestMapping(value = "/base_model/weixin_service/weixinPublicTempalte")
public class WeixinPublicTempalteController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IWeixinPublicTempalteService weixinPublicTempalteService;

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
