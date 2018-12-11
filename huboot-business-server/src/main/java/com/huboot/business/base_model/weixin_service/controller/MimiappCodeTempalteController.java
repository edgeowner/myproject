package com.huboot.business.base_model.weixin_service.controller;

import com.huboot.business.base_model.weixin_service.dto.WeixinMimiappCodeTemplateDTO;
import com.huboot.business.base_model.weixin_service.dto.WxCodeTemplate;
import com.huboot.business.base_model.weixin_service.dto.util.Pager;
import com.huboot.business.base_model.weixin_service.service.IWeixinMimiappCodeTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.open.bean.WxOpenMaCodeTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "小程序代码模板信息表 API")
@RestController
@RequestMapping(value = "/base_model/weixin_service/miniapp_tempalte")
public class MimiappCodeTempalteController {

	@Autowired
	private IWeixinMimiappCodeTemplateService weixinMimiappCodeTemplateService;

	@GetMapping("/getDraftList")
	@ApiOperation("获取代码草稿箱")
	public List<WxOpenMaCodeTemplate> getDraftList() throws Exception {
		return weixinMimiappCodeTemplateService.getDraftList();
	}

	@PostMapping(value = "/becomeTemplate/{draftId}")
	@ApiOperation("草稿成为代码模板")
	public void becomeTemplate(@PathVariable("draftId") Long draftId) throws Exception {
		weixinMimiappCodeTemplateService.becomeTemplate(draftId);
	}

	@GetMapping("/getTemplateList")
	@ApiOperation("获取微信方小程序代码模板")
	public List<WxCodeTemplate> getTemplateList()throws Exception {
		return weixinMimiappCodeTemplateService.getTemplateList();
	}

	@PostMapping(value = "/deleteTemplate/{templateId}")
	@ApiOperation("删除模板")
	public void deleteTemplate(@PathVariable("templateId") Integer templateId) throws Exception {
		weixinMimiappCodeTemplateService.deleteTemplate(templateId);
	}

	@PostMapping(value = "/becomeSystemTemplate/{templateId}")
	@ApiOperation("将代码模板添加到系统中去")
	public void becomeSystemTemplate(@PathVariable("templateId") Integer templateId) throws Exception {
		weixinMimiappCodeTemplateService.becomeSystemTemplate(templateId);
	}


	@GetMapping("/systemTemplatePager")
	@ApiOperation("系统代码模板")
	public Pager<WeixinMimiappCodeTemplateDTO> systemTemplatePager(@RequestParam(value = "templateId", required = false)Integer templateId,
																   @RequestParam(value = "userVersion", required = false)String userVersion,
																   @RequestParam("page")Integer page,
																   @RequestParam("size")Integer size) throws Exception {
		return weixinMimiappCodeTemplateService.systemTemplatePager(templateId, userVersion, page, size);
	}

	@GetMapping("/systemTemplateDetail/{ctid}")
	@ApiOperation("系统代码模板详情")
	public WeixinMimiappCodeTemplateDTO systemTemplateDetail(@PathVariable("ctid") Integer ctid) {
		return weixinMimiappCodeTemplateService.systemTemplateDetail(ctid);
	}


	@PostMapping(value = "/updateSystemTemplate/{ctid}")
	@ApiOperation("修改系统代码模板参数")
	public void updateSystemTemplate(@PathVariable("ctid") Integer ctid, @RequestBody WeixinMimiappCodeTemplateDTO dto) throws Exception {
		weixinMimiappCodeTemplateService.updateSystemTemplate(ctid, dto);
	}

}
