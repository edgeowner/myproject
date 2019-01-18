package com.huboot.user.weixin.controller.inner;

import com.huboot.share.user_service.api.dto.QrcodeCreateDTO;
import com.huboot.share.user_service.api.feign.MiniAppFeignCilent;
import com.huboot.user.weixin.service.IMiniappService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "内部端-小程序 API")
@RestController
public class InnerMiniappController implements MiniAppFeignCilent {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IMiniappService miniappService;

	@Override
	public String createQrcode(@RequestBody QrcodeCreateDTO createDTO) {
		return miniappService.createQrcode(createDTO);
	}

	@Override
	public String createPathQrcode(@RequestBody QrcodeCreateDTO createDTO) {
		return miniappService.createPathQrcode(createDTO);
	}
}
