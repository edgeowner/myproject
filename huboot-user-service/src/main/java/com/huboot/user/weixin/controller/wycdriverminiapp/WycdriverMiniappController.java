package com.huboot.user.weixin.controller.wycdriverminiapp;

import com.huboot.user.weixin.service.IMiniappService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "网约车司机小程序端-小程序 API")
@RestController
@RequestMapping(value = "/wycdriverminiapp/weixin/miniapp")
public class WycdriverMiniappController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IMiniappService miniappService;

}
