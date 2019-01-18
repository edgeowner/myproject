package com.huboot.user.weixin.controller.inner;

import com.huboot.share.user_service.api.dto.QrcodeCreateDTO;
import com.huboot.share.user_service.api.dto.WxmpSendMessageDTO;
import com.huboot.share.user_service.api.feign.WxmpFeignCilent;
import com.huboot.user.weixin.service.IWxmpService;
import com.huboot.user.weixin.service.IWxmpTemplateRelationService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "内部端-公众号 API")
@RestController
public class InnerWxmpController implements WxmpFeignCilent {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IWxmpTemplateRelationService templateRelationService;
	@Autowired
	private IWxmpService wxmpService;

	@Override
	public void sendMessage(@RequestBody WxmpSendMessageDTO messageDTO) {
		templateRelationService.sendMessage(messageDTO);
	}

	@Override
	public String createQrcode(@RequestBody QrcodeCreateDTO createDTO) {
		return wxmpService.createQrcode(createDTO);
	}

}
