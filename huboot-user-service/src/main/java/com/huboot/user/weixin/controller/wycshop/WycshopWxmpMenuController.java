package com.huboot.user.weixin.controller.wycshop;

import com.huboot.user.weixin.dto.wycshop.WxmpMenuParentDTO;
import com.huboot.user.weixin.service.IWxmpMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "网约车管理端-微信公众号菜单 API")
@RestController
@RequestMapping(value = "/wycshop/weixin/wxmpMenu")
public class WycshopWxmpMenuController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IWxmpMenuService wxmpMenuService;

	@GetMapping(value = "/getWxmpSetMenuList")
	@ApiOperation(response = void.class, value = "获取公众号设置菜单集合")
	public ModelMap getWeixinMentList() throws Exception {
		return wxmpMenuService.getWxmpSetMenuList();
	}

	@PostMapping(value = "/saveWxmpMenuList")
	@ApiOperation(response = void.class, value = "保存公众号菜单")
	public void saveWxmpMenuList(@RequestBody List<WxmpMenuParentDTO> menuList) throws Exception {
		wxmpMenuService.saveWxmpMenuList(menuList);
	}
}
