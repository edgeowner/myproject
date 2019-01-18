package com.huboot.user.weixin.controller.admin;

import com.huboot.commons.page.ShowPageImpl;
import com.huboot.user.weixin.dto.MiniappCodeTemplateDTO;
import com.huboot.user.weixin.dto.admin.WxCodeTemplate;
import com.huboot.user.weixin.service.IMiniappCodeTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.open.bean.WxOpenMaCodeTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "内管端-小程序系统代码模板 API")
@RestController
@RequestMapping(value = "/admin/weixin/miniapp/code_tempalte")
public class AdminCodeTempalteController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IMiniappCodeTemplateService codeTemplateService;

	@GetMapping("/get_draft_list")
	@ApiOperation("获取代码草稿箱")
	public List<WxOpenMaCodeTemplate> getDraftList() throws Exception {
		return codeTemplateService.getDraftList();
	}

	@PostMapping("/become_template/{draftId}")
	@ApiOperation("草稿成为代码模板")
	public void becomeTemplate(@PathVariable("draftId") Long draftId) throws Exception {
		codeTemplateService.becomeTemplate(draftId);
	}

	@PostMapping("/delete_template/{templateId}")
	@ApiOperation("删除模板")
	public void deleteTemplate(@PathVariable("templateId") Long templateId) throws Exception {
		codeTemplateService.deleteTemplate(templateId);
	}


	@GetMapping("/get_template_list")
	@ApiOperation("获取微信方小程序代码模板")
	public List<WxCodeTemplate> getTemplateList()throws Exception {
		return codeTemplateService.getTemplateList();
	}


	@PostMapping("/get_and_add_template/{templateId}")
	@ApiOperation("获取并添加代码模板")
	public void getAndAddTemplate(@PathVariable("templateId") Long templateId) throws Exception {
		codeTemplateService.getAndAddTemplate(templateId);
	}



	@GetMapping("/pager")
	@ApiOperation("获取小程序发布记录分页列表")
	public ShowPageImpl<MiniappCodeTemplateDTO> getPager(@RequestParam("page") Integer page,
														 @RequestParam("size") Integer size) {
		return codeTemplateService.getPager(page, size);
	}

}
