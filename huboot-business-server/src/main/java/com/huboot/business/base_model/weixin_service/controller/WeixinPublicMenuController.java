package com.huboot.business.base_model.weixin_service.controller;

import com.huboot.business.base_model.weixin_service.dto.WeixinPublicMenuDTO;
import com.huboot.business.base_model.weixin_service.dto.dto.InitPublicFromMPDTO;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.WeixinPublicMenuParentDTO;
import com.huboot.business.base_model.weixin_service.service.IWeixinPublicMenuService;

import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Api(tags = "微信设置-微信公众号菜单 API")
@RestController
@RequestMapping(value = "/base_model/weixin_service/weixinPublicMenu")
public class WeixinPublicMenuController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IWeixinPublicMenuService weixinPublicMenuService;

	@PostMapping(value = "/")
	@ApiOperation(response = void.class, value = "创建")
	public void post(@RequestBody WeixinPublicMenuDTO dto) throws Exception {
		weixinPublicMenuService.create(dto);
	}

	@PostMapping(value = "/initMenu2")
	@ApiOperation(response = void.class, value = "同步菜单到公众号")
	public void initMenu2(@RequestBody InitPublicFromMPDTO initPublicFromMPDTO) throws Exception {
		weixinPublicMenuService.initMenu(initPublicFromMPDTO.getWeixinUid(), 2, initPublicFromMPDTO.getShopUid());
	}

	@GetMapping(value = "/getMenuFromWX/{weixinUid}")
	@ApiOperation(response = void.class, value = "从微信服务器获取公众号菜单")
	public WxMpMenu getMenuFromWX(@PathVariable("weixinUid") String weixinUid) throws Exception {
		return weixinPublicMenuService.getMenuFromWX(weixinUid);
	}

	@GetMapping(value = "/getWeixinMentList/{weixinUid}")
	@ApiOperation(response = void.class, value = "获取公众号菜单")
	public List<WeixinPublicMenuParentDTO> getWeixinMentList(@PathVariable("weixinUid") String weixinUid) throws Exception {
		return weixinPublicMenuService.getWeixinMentList(weixinUid);
	}

	@PostMapping(value = "/saveWeixinMentList/{weixinUid}")
	@ApiOperation(response = void.class, value = "保存公众号菜单")
	public void saveWeixinMentList(@PathVariable("weixinUid") String weixinUid, @RequestBody List<WeixinPublicMenuParentDTO> menuList) throws Exception {
		weixinPublicMenuService.saveWeixinMentList(weixinUid, menuList);
	}
}
