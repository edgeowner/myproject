package com.huboot.user.weixin.controller.wycshop;

import com.huboot.user.weixin.dto.WxmpUpdateDTO;
import com.huboot.user.weixin.dto.wycshop.OpenAuthBindDTO;
import com.huboot.user.weixin.dto.wycshop.WxmpappInfoDTO;
import com.huboot.user.weixin.service.IWxmpService;
import com.huboot.user.weixin.service.IWxmpTemplateRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "网约车管理端-公众号 API")
@RestController
@RequestMapping(value = "/wycshop/weixin/wxmp")
public class WycshopWxmpController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IWxmpService wxmpService;
	@Autowired
	private IWxmpTemplateRelationService wxmpTemplateRelationService;

	@GetMapping(value = "/find_wxmpapp_info")
	@ApiOperation("查询公众号信息")
	public WxmpappInfoDTO findWxmpappInfo() {
		return wxmpService.findWxmpappInfo();
	}

	@PostMapping(value = "/auth_and_bind_shop")
	@ApiOperation("公众号授权")
	public WxmpappInfoDTO authAndBindShop(@Validated @RequestBody OpenAuthBindDTO reqDTO) {
		WxmpappInfoDTO infoDTO =  wxmpService.authAndBindShop(reqDTO.getAuthorizationCode());
		//为公众号配置所有的消息模板
		try {
			wxmpTemplateRelationService.addAllTemplateForWxmp(infoDTO.getWxmpId());
		} catch (Exception e) {
			logger.error("公众号初始化所有的消息模板", e);
		}
		return infoDTO;
	}


	@PostMapping(value = "/subscribe_reply")
	@ApiOperation("设置关注回复")
	public void saveSubscribeReply(@RequestBody WxmpUpdateDTO wxmpUpdateDTO) {
		wxmpService.saveSubscribeReply(wxmpUpdateDTO);
	}
}
