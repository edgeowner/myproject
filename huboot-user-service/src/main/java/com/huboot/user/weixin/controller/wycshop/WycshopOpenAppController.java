package com.huboot.user.weixin.controller.wycshop;

import com.huboot.user.weixin.service.IOpenAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(tags = "网约车管理端-开放平台 API")
@RestController
@RequestMapping(value = "/wycshop/weixin/openapp/")
public class WycshopOpenAppController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private IOpenAppService openAppService;

	@PostMapping(value = "/get_preauth_url")
	@ApiOperation("获取平台授权路径")
	public String getPreAuthUrl(@RequestBody Map<String, String> map) {
		return openAppService.getPreAuthUrl(map.get("url"));
	}
}
