package com.huboot.user.weixin.controller.admin;

import com.huboot.commons.page.ShowPageImpl;
import com.huboot.share.common.enums.YesOrNoEnum;
import com.huboot.user.weixin.dto.WxmpMessageTemplateDTO;
import com.huboot.user.weixin.dto.admin.WxmpMessageTemplateCreateDTO;
import com.huboot.user.weixin.dto.admin.WxmpMessageTemplatePagerDTO;
import com.huboot.user.weixin.entity.WxmpMessageTemplateEntity;
import com.huboot.user.weixin.service.IWxmpMessageTemplateService;
import com.huboot.user.weixin.service.IWxmpTemplateRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "内管端-公众号 API")
@RestController
@RequestMapping(value = "/admin/weixin/wxmp_message_template")
public class AdminWxmpMessageTemplateController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IWxmpMessageTemplateService wxmpMessageTemplateService;
	@Autowired
	private IWxmpTemplateRelationService wxmpTemplateRelationService;

	@PostMapping("/create_template")
	@ApiOperation("添加微信消息模板")
	public void createTmplate(@Validated @RequestBody WxmpMessageTemplateCreateDTO createDTO) {
		WxmpMessageTemplateEntity templateEntity = wxmpMessageTemplateService.createTemplate(createDTO);
		if(YesOrNoEnum.yes.name().equals(createDTO.getConfig())) {
			wxmpTemplateRelationService.addTemplateForAllWxmp(templateEntity.getId());
		}
	}

	@GetMapping("/pager")
	@ApiOperation("微信消息模板分页")
	public ShowPageImpl<WxmpMessageTemplatePagerDTO> pager(@RequestParam(value = "node", required = false) String node,
														   @RequestParam(value = "templateIdShort", required = false) String templateIdShort,
														   @RequestParam("page") Integer page,
														   @RequestParam("size") Integer size) {
		return wxmpMessageTemplateService.pager(node, templateIdShort, page, size);
	}

	@GetMapping("/info/{id}")
	@ApiOperation("微信消息模板信息")
	public WxmpMessageTemplateDTO info(@PathVariable("id")Long id) {
		return wxmpMessageTemplateService.findById(id);
	}

	@PostMapping("/edit/{id}")
	@ApiOperation("微信消息模板信息")
	public void edit(@PathVariable("id")Long id, @RequestBody WxmpMessageTemplateDTO templateDTO) {
		templateDTO.setId(id);
		wxmpMessageTemplateService.edit(templateDTO);
	}
}
