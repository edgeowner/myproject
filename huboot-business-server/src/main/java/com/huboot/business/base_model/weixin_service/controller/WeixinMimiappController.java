package com.huboot.business.base_model.weixin_service.controller;

import com.huboot.business.base_model.weixin_service.dto.MiniappPagerDTO;
import com.huboot.business.base_model.weixin_service.dto.dto.WeixinAuthDTO;
import com.huboot.business.base_model.weixin_service.dto.util.Pager;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.QrCodeDTO;
import com.huboot.business.base_model.weixin_service.service.IMiniAppService;
import com.huboot.business.base_model.weixin_service.service.IWeixinPublicSettingService;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

import java.util.Map;

@Api(tags = "小程序信息表 API")
@RestController
@RequestMapping(value = "/base_model/weixin_service/weixinMimiapp")
public class WeixinMimiappController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IMiniAppService miniAppService;
	@Autowired
	private IWeixinPublicSettingService settingService;

	@PostMapping(value = "/youhua")
	@ApiOperation(response = void.class, value = "优化")
	public void youhua() throws Exception {
		miniAppService.youhua();
	}

	@GetMapping(value = "/getMiniAppOpenId")
	@ApiOperation(response = String.class, value = "获取小程序openid")
	public WeixinAuthDTO getMiniAppOpenId(@RequestParam("miniAppId") String weixinUid, @RequestParam("wxcode") String wxcode) throws Exception{
		return miniAppService.getMiniAppOpenId(weixinUid, wxcode);
	}

	@PostMapping(value = "/bitchCommitDomain")
	@ApiOperation(response = String.class, value = "批量设置小程序域名")
	public void bitchCommitDomain() throws Exception {
		miniAppService.bitchCommitDomain();
	}

	@PostMapping(value = "/bitchCommitViewDomain")
	@ApiOperation(response = String.class, value = "批量设置小程序业务域名")
	public void bitchCommitViewDomain() throws Exception {
		miniAppService.bitchCommitViewDomain();
	}

	@PostMapping(value = "/setWeappSupportVersion/{weixinUid}")
	@ApiOperation(response = String.class, value = "设置最低基础库版本")
	public void setWeappSupportVersion(@PathVariable("weixinUid") String weixinUid, @RequestBody Map<String, String> map) throws Exception {
		String version = map.get("version");
		miniAppService.setWeappSupportVersion(weixinUid, version);
	}

	@GetMapping(value = "/miniappPager")
	@ApiOperation(response = String.class, value = "小程序列表")
	public Pager<MiniappPagerDTO> miniappPager(@RequestParam(value = "shopUid", required = false)String shopUid,
											   @RequestParam(value = "miniappUid", required = false)String miniappUid,
											   @RequestParam("page")Integer page,
											   @RequestParam("size")Integer size) {
		return miniAppService.miniappPager(shopUid, miniappUid, page, size);
	}

	@GetMapping(value = "/getSetInfo/{weixinUid}")
	@ApiOperation(response = String.class, value = "获取小程序设置信息")
	public Map<String, String> getSetInfo(@PathVariable("weixinUid") String weixinUid) {
		return settingService.getSetInfo(weixinUid);
	}

	@PostMapping(value = "/getAndCreateMiniQrCode")
	@ApiOperation(response = String.class, value = "获取小程序二维码")
	public QrCodeDTO getAndCreateMiniQrCode(@RequestBody Map<String, String> map) throws Exception {
		return miniAppService.getAndCreateMiniQrCode(map);
	}

	@GetMapping(value = "/getExperienceQrcode/{miniappUid}")
	@ApiOperation(response = String.class, value = "获取小程序体验吗")
	public String getExperienceQrcode(@PathVariable("miniappUid") String miniappUid,
									  @RequestParam("path")String path) throws Exception {
		return miniAppService.getExperienceQrcode(miniappUid, path);
	}

	@GetMapping(value = "/getMiniappQrcode/{miniappUid}")
	@ApiOperation(response = String.class, value = "获取小程二维码")
	public String getMiniappQrcode(@PathVariable("miniappUid") String miniappUid) throws Exception {
		return miniAppService.getMiniappQrcode(miniappUid);
	}

	@GetMapping(value = "/getMiniAppPhone")
	@ApiOperation(response = String.class, value = "获取小程序用户手机号")
	public String getMiniappPhone(@RequestParam("miniAppId") String weixinUid, @RequestParam("encryptedData") String encryptedData,
								  @RequestParam("iv") String iv, @RequestParam("openId") String openId) throws Exception {
		return miniAppService.getMiniPhone(weixinUid, encryptedData, iv, openId);
	}

}
