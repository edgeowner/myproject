package com.huboot.user.weixin.controller.wycshop;

import com.huboot.share.user_service.data.UserCacheData;
import com.huboot.user.weixin.dto.wycshop.MiniappInfoDTO;
import com.huboot.user.weixin.dto.wycshop.OpenAuthBindDTO;
import com.huboot.user.weixin.service.IMiniappService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Api(tags = "网约车管理端-小程序 API")
@RestController
@RequestMapping(value = "/wycshop/weixin/miniapp/")
public class WycshopMiniappController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IMiniappService miniappService;
	@Autowired
	private UserCacheData userCacheData;

	@GetMapping(value = "/find_miniapp_info")
	@ApiOperation("查询小程序信息")
	public MiniappInfoDTO findMiniappInfo() {
		return miniappService.findMiniappInfo(userCacheData.getCurrentUserWycShopId());
	}

	@PostMapping(value = "/auth_and_bind_shop")
	@ApiOperation("小程序授权")
	public MiniappInfoDTO authAndBindShop(@Validated @RequestBody OpenAuthBindDTO reqDTO) {
		return miniappService.authAndBindShop(reqDTO.getAuthorizationCode(), userCacheData.getCurrentUserWycShopId());
	}
}
