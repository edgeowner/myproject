package com.huboot.user.weixin.controller.admin;

import com.huboot.commons.page.ShowPageImpl;
import com.huboot.user.common.constant.WeixinConstant;
import com.huboot.user.weixin.dto.admin.MiniappPagerDTO;
import com.huboot.user.weixin.dto.admin.MiniappSettingDTO;
import com.huboot.user.weixin.service.IMiniappConfigService;
import com.huboot.user.weixin.service.IMiniappService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = "内管端-小程序 API")
@RestController
@RequestMapping(value = "/admin/weixin/miniapp")
public class AdminMiniappController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IMiniappService miniappService;
	@Autowired
	private IMiniappConfigService configService;


	@PostMapping("/set_can_bitch_release/{miniappId}/{yesOrNo}")
	@ApiOperation("设置小程序能否批量发布")
	public void setCanBitchRelease(@PathVariable("miniappId")String miniappId, @PathVariable("yesOrNo")String yesOrNo) {
		miniappService.setCanBitchRelease(miniappId, yesOrNo);
	}

	@PostMapping("/set_server_domain/{miniappId}")
	@ApiOperation("配置服务器域名")
	public void setServerDomain(@PathVariable("miniappId")String miniappId) {
		configService.configServerDomain(miniappId);
	}

	@PostMapping("/set_base_version/{miniappId}")
	@ApiOperation("配置基础库版本")
	public void setBaseVersion(@PathVariable("miniappId")String miniappId, @RequestBody Map<String, String> map) {
		String version = WeixinConstant.DEFAULT_VERSION;
		if(map != null) {
			version = map.get("version") == null ? WeixinConstant.DEFAULT_VERSION : map.get("version").toString();
		}
		configService.configBaseLibraryVersion(miniappId, version);
	}

	@PostMapping("/setting/{miniappId}")
	@ApiOperation("小程序设置")
	public void setting(@PathVariable("miniappId")String miniappId) {
		configService.configBaseLibraryVersion(miniappId, WeixinConstant.DEFAULT_VERSION);
		configService.configServerDomain(miniappId);
	}

	@PostMapping("/bitch_set_server_domain")
	@ApiOperation("小程序设置")
	public void bitchSetServerDomain() {
		configService.bitchSetServerDomain();
	}


	@GetMapping("/get_setting_info/{miniappId}")
	@ApiOperation("获取小程序设置信息")
	public MiniappSettingDTO getSettingInfo(@PathVariable("miniappId")String miniappId) {
		return configService.getSettingInfo(miniappId);
	}

	@GetMapping("/get_qrcode_img/{miniappId}")
	@ApiOperation("获取小程序二维码")
	public String getQrcodeImg(@PathVariable("miniappId")String miniappId) {
		return miniappService.getQrcodeImg(miniappId);
	}

	@PostMapping("/get_experience_qrcode_img/{miniappId}")
	@ApiOperation("获取小程序体验码")
	public String getExperienceQrcodeImg(@PathVariable("miniappId")String miniappId, @RequestBody Map map) throws Exception{
		String path = map.get("path").toString();
		return miniappService.getExperienceQrcodeImg(miniappId, path);
	}



	@GetMapping("/pager")
	@ApiOperation("获取小程序分页列表")
	public ShowPageImpl<MiniappPagerDTO> getPager(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
		return miniappService.getPager(page, size);
	}

}
