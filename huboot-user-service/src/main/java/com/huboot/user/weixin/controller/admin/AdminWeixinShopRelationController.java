package com.huboot.user.weixin.controller.admin;

import com.huboot.commons.page.ShowPageImpl;
import com.huboot.user.weixin.dto.admin.WeixinShopRelationPagerDTO;
import com.huboot.user.weixin.service.IWeixinShopRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "内管端-微信店铺关系 API")
@RestController
@RequestMapping(value = "/admin/weixin/shop_relation")
public class AdminWeixinShopRelationController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IWeixinShopRelationService weixinShopRelationService;

	@PostMapping("/init_openapp")
	@ApiOperation("初始化openapp")
	public void initOpenapp() throws Exception {
		weixinShopRelationService.initOpenapp();
	}

	@GetMapping("/pager")
	@ApiOperation("获取小程序发布记录分页列表")
	public ShowPageImpl<WeixinShopRelationPagerDTO> getPager(@RequestParam("page") Integer page,
															 @RequestParam("size") Integer size) {
		return weixinShopRelationService.getPager(page, size);
	}
}
