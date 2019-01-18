package com.huboot.user.weixin.controller.admin;

import com.huboot.commons.page.ShowPageImpl;
import com.huboot.user.weixin.dto.admin.WxmpPagerDTO;
import com.huboot.user.weixin.service.IWxmpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "内管端-公众号 API")
@RestController
@RequestMapping(value = "/admin/weixin/wxmp")
public class AdminWxmpController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IWxmpService wxmpService;

	@GetMapping("/get_qrcode_img/{wxmpappId}")
	@ApiOperation("获取小程序二维码")
	public String getQrcodeImg(@PathVariable("wxmpappId")String wxmpappId) {
		return wxmpService.getQrcodeImg(wxmpappId);
	}


	@GetMapping("/pager")
	@ApiOperation("获取公众号分页列表")
	public ShowPageImpl<WxmpPagerDTO> getPager(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
		return wxmpService.getPager(page, size);
	}
}
