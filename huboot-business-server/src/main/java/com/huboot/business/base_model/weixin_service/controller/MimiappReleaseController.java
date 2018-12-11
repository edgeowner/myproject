package com.huboot.business.base_model.weixin_service.controller;

import com.huboot.business.base_model.weixin_service.dto.ReleaseLogDetailDTO;
import com.huboot.business.base_model.weixin_service.dto.ReleaseLogPagerDTO;
import com.huboot.business.base_model.weixin_service.dto.util.Pager;
import com.huboot.business.base_model.weixin_service.service.IMiniAppReleaseService;
import com.huboot.business.base_model.weixin_service.dto.WeixinMimiappPagerDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Api(tags = "小程序发布信息 API")
@RestController
@RequestMapping(value = "/base_model/weixin_service/release_miniapp")
public class MimiappReleaseController {

	@Autowired
	private IMiniAppReleaseService appReleaseService;


	@PostMapping(value = "/commitCode/{miniappUid}/{templateId}")
	@ApiOperation(response = String.class, value = "小程序代码提交")
	public Integer commitCode(@PathVariable("miniappUid") String miniappUid,
						   @PathVariable("templateId") Integer templateId) throws Exception {
		return appReleaseService.commitCode(templateId, miniappUid);
	}

	@PostMapping(value = "/commitCheck/{releaseLogId}/{releaseAfterAudit}")
	@ApiOperation(response = String.class, value = "小程序提交审核")
	public void commitCheck(@PathVariable("releaseLogId") Integer releaseLogId,
							@PathVariable("releaseAfterAudit") Integer releaseAfterAudit) throws Exception {
		appReleaseService.commitCheck(releaseLogId, releaseAfterAudit);
	}

	@PostMapping(value = "/revokeCheck/{releaseLogId}")
	@ApiOperation(response = String.class, value = "小程序撤销审核")
	public void revokeCheck(@PathVariable("releaseLogId") Integer releaseLogId) throws Exception {
		appReleaseService.revokeCheck(releaseLogId);
	}

	@PostMapping(value = "/reCommitCheck/{releaseLogId}")
	@ApiOperation(response = String.class, value = "小程序重新提交审核")
	public void reCommitCheck(@PathVariable("releaseLogId") Integer releaseLogId) throws Exception {
		appReleaseService.reCommitCheck(releaseLogId);
	}

	@GetMapping(value = "/getCheckResult/{releaseLogId}")
	@ApiOperation(response = String.class, value = "获取小程序审核结果")
	public String getCheckResult(@PathVariable("releaseLogId") Integer releaseLogId) throws Exception {
		return appReleaseService.getCheckResult(releaseLogId);
	}

	@PostMapping(value = "/release/{releaseLogId}")
	@ApiOperation(response = String.class, value = "小程序发布")
	public void release(@PathVariable("releaseLogId") Integer releaseLogId) throws Exception {
		appReleaseService.release(releaseLogId);
	}


	@PostMapping(value = "/commitCodeAndCheck/{miniappUid}/{templateId}/{releaseAfterAudit}")
	@ApiOperation(response = String.class, value = "提交代码和提交审核")
	public void commitCodeAndCheck(@PathVariable("miniappUid") String miniappUid,
								   @PathVariable("templateId") Integer templateId,
								   @PathVariable("releaseAfterAudit") Integer releaseAfterAudit) throws Exception {
		Integer logId = appReleaseService.commitCode(templateId, miniappUid);
		appReleaseService.commitCheck(logId, releaseAfterAudit);
	}

	@PostMapping(value = "/bitchUpdateVersion/{templateId}/{releaseAfterAudit}")
	@ApiOperation(response = void.class, value = "小程序批量更新版本")
	public void bitchUpdateVersion(@PathVariable("templateId") Integer templateId,
								   @PathVariable("releaseAfterAudit") Integer releaseAfterAudit,
								   @RequestBody Map<String, String> map) throws Exception {
		String exclude = map.get("exclude");
		appReleaseService.bitchUpdateVersion(templateId, releaseAfterAudit, exclude);
	}

	@PostMapping(value = "/bitchRelease/{templateId}")
	@ApiOperation(response = void.class, value = "小程序批量部署版本")
	public void bitchRelease(@PathVariable("templateId") Integer templateId) throws Exception {
		appReleaseService.bitchRelease(templateId);
	}


	//==========================================================

	@GetMapping(value = "/releasePager")
	@ApiOperation(response = void.class, value = "小程序版本列表-供运营使用")
	public Pager<WeixinMimiappPagerDTO> releasePager(@RequestParam(value = "shopUid", required = false)String shopUid,
                                                     @RequestParam(value = "userVersion", required = false)String userVersion,
                                                     @RequestParam("page")Integer page,
                                                     @RequestParam("size")Integer size) throws Exception {
		return appReleaseService.releasePager(shopUid, userVersion, page, size);
	}

	@GetMapping(value = "/releaseLogPager")
	@ApiOperation(response = void.class, value = "发布日志")
	public Pager<ReleaseLogPagerDTO> releaseLogPager(@RequestParam(value = "miniappUid", required = false)String miniappUid,
                                                     @RequestParam(value = "templateId", required = false)Integer templateId,
                                                     @RequestParam(value = "status", required = false)Integer status,
                                                     @RequestParam("page")Integer page,
                                                     @RequestParam("size")Integer size) {
		return appReleaseService.releaseLogPager(miniappUid, templateId, status, page, size);
	}


	@GetMapping(value = "/releaseDetail/{releaseLogId}")
	@ApiOperation(response = void.class, value = "发布日志详情")
	public ReleaseLogDetailDTO releaseDetail(@PathVariable("releaseLogId") Integer releaseLogId) {
		return appReleaseService.releaseDetail(releaseLogId);
	}
}
