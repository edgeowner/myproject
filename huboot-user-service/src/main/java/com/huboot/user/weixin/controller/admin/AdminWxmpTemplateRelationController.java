package com.huboot.user.weixin.controller.admin;

import com.huboot.commons.page.ShowPageImpl;
import com.huboot.user.weixin.dto.admin.WxmpMessageLogDetailDTO;
import com.huboot.user.weixin.dto.admin.WxmpMessageLogPagerDTO;
import com.huboot.user.weixin.dto.admin.WxmpTemplateRelationPagerDTO;
import com.huboot.user.weixin.service.IWxmpTemplateRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "内管端-公众号 API")
@RestController
@RequestMapping(value = "/admin/weixin/wxmp_template_relation")
public class AdminWxmpTemplateRelationController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IWxmpTemplateRelationService wxmpTemplateRelationService;


	@PostMapping("/add_template/for_all_wxmp/{relaTemplateId}")
	@ApiOperation("将消息模板添加给所有公众号")
	public void addTemplateForAllWxmp(@PathVariable("relaTemplateId")Long relaTemplateId) {
		wxmpTemplateRelationService.addTemplateForAllWxmp(relaTemplateId);
	}

	@PostMapping("/add_all_template/for_wxmp/{wxmpappId}")
	@ApiOperation("为公众号添加所有模板消息")
	public void addAllTemplateForWxmp(@PathVariable("wxmpappId")String wxmpappId) {
		wxmpTemplateRelationService.addAllTemplateForWxmp(wxmpappId);
	}

	@PostMapping("/add_template/for_wxmp/{wxmpappId}/{relaTemplateId}")
	@ApiOperation("为一个公众号添加一个模板消息")
	public void addTemplateForWxmp(@PathVariable("wxmpappId")String wxmpappId,
									  @PathVariable("relaTemplateId")Long relaTemplateId) {
		wxmpTemplateRelationService.addTemplateForWxmp(wxmpappId, relaTemplateId);
	}

	@GetMapping("/pager")
	@ApiOperation("微信消息模板分页")
	public ShowPageImpl<WxmpTemplateRelationPagerDTO> pager(@RequestParam(value = "node", required = false) String node,
															@RequestParam(value = "wxmpappId", required = false) String wxmpappId,
															@RequestParam("page") Integer page,
															@RequestParam("size") Integer size) {
		return wxmpTemplateRelationService.pager(node, wxmpappId, page, size);
	}

	@GetMapping("/log_pager")
	@ApiOperation("微信消息日志分页")
	public ShowPageImpl<WxmpMessageLogPagerDTO> logPager(@RequestParam(value = "node", required = false) String node,
														 @RequestParam(value = "wxmpappId", required = false) String wxmpappId,
														 @RequestParam("page") Integer page,
														 @RequestParam("size") Integer size) {
		return wxmpTemplateRelationService.logPager(node, wxmpappId, page, size);
	}

	@GetMapping("/log_detail/{logId}")
	@ApiOperation("微信消息日志分页")
	public WxmpMessageLogDetailDTO logDetail(@PathVariable("logId")Long logId) {
		return wxmpTemplateRelationService.logDetail(logId);
	}
}
