package com.huboot.user.weixin.controller.admin;

import com.huboot.user.weixin.service.IOpenAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "内管端-小程序 API")
@RestController
@RequestMapping(value = "/admin/weixin/openapp")
public class AdminOpenAppController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IOpenAppService openAppService;

	@GetMapping("/get_access_token/{appId}")
	@ApiOperation("获取小程序AccessToken")
	public String getAccessToken(@PathVariable("appId")String appId) throws Exception {
		return openAppService.getAccessToken(appId);
	}

}
