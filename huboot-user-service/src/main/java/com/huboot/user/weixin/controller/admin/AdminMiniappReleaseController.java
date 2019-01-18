package com.huboot.user.weixin.controller.admin;

import com.huboot.commons.page.ShowPageImpl;
import com.huboot.share.common.enums.YesOrNoEnum;
import com.huboot.user.weixin.dto.admin.AuditResultDTO;
import com.huboot.user.weixin.dto.admin.MiniappReleaseLogDetail;
import com.huboot.user.weixin.dto.admin.MiniappReleaseLogPagerDTO;
import com.huboot.user.weixin.entity.MiniappReleaseLogEntity;
import com.huboot.user.weixin.service.IMiniappReleaseLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "内管端-小程序发布记录 API")
@RestController
@RequestMapping(value = "/admin/weixin/miniapp/release")
public class AdminMiniappReleaseController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IMiniappReleaseLogService releaseLogService;

	private static final String checkList =
			"{\n" +
			"\t\"item_list\": [{\n" +
			"\t\t\"address\": \"pages/recruit/recruit\",\n" +
			"\t\t\"tag\": \"出行 交通\",\n" +
			"\t\t\"first_class\": \"出行与交通\",\n" +
			"\t\t\"second_class\": \"租车\",\n" +
			"\t\t\"first_id\": \"110\",\n" +
			"\t\t\"second_id\": \"129\",\n" +
			"\t\t\"title\": \"司机招募\"\n" +
			"\t}, {\n" +
			"\t\t\"address\": \"pages/index/index\",\n" +
			"\t\t\"tag\": \"出行 交通\",\n" +
			"\t\t\"first_class\": \"出行与交通\",\n" +
			"\t\t\"second_class\": \"租车\",\n" +
			"\t\t\"first_id\": \"110\",\n" +
			"\t\t\"second_id\": \"129\",\n" +
			"\t\t\"title\": \"首页\"\n" +
			"\t}]\n" +
			"}";


	@PostMapping(value = "/commit_code/{miniappId}/{templateId}")
	@ApiOperation("提交代码")
	public Long commitCode(@PathVariable("miniappId")String miniappId,
											  @PathVariable("templateId")String templateId) {
		MiniappReleaseLogEntity logEntity = releaseLogService.commitCode(miniappId, templateId);
		return logEntity.getId();
	}

	@PostMapping(value = "/commit_check/{releaseLogId}/{releaseAfterAudit}")
	@ApiOperation("提交审核")
	public void commitCheck(@PathVariable("releaseLogId") Long releaseLogId,
											   @PathVariable("releaseAfterAudit") String releaseAfterAudit) {
		YesOrNoEnum yesOrNoEnum = YesOrNoEnum.no;
		if("yes".equals(releaseAfterAudit)) {
			yesOrNoEnum = YesOrNoEnum.yes;
		}
		releaseLogService.commitCheck(releaseLogId, yesOrNoEnum, checkList);
	}

	@PostMapping(value = "/revoke_check/{miniappId}")
	@ApiOperation("撤销审核")
	public void undoCodeAudit(@PathVariable("miniappId")String miniappId) throws Exception {
		releaseLogService.undoCodeAudit(miniappId);
	}

	@PostMapping(value = "/commit_code_and_check/{miniappId}/{templateId}/{releaseAfterAudit}")
	@ApiOperation("提交代码和审核")
	public void commitCodeAndCheck(@PathVariable("miniappId")String miniappId,
													  @PathVariable("templateId")String templateId,
													  @PathVariable("releaseAfterAudit") String releaseAfterAudit) {
		YesOrNoEnum yesOrNoEnum = YesOrNoEnum.no;
		if("yes".equals(releaseAfterAudit)) {
			yesOrNoEnum = YesOrNoEnum.yes;
		}
		MiniappReleaseLogEntity logEntity = releaseLogService.commitCode(miniappId, templateId);
		releaseLogService.commitCheck(logEntity.getId(), yesOrNoEnum, checkList);
	}

	@GetMapping("/get_audit_result_with_auditId/{auditId}")
	@ApiOperation("查询微信官方审核结构")
	public AuditResultDTO getAuditResultWithAuditId(@PathVariable("auditId")String auditId) throws Exception {
		return releaseLogService.getAuditResultWithAuditId(auditId);
	}

	@GetMapping("/get_audit_result/{miniappId}")
	@ApiOperation("获取小程序最新审核结果")
	public AuditResultDTO getAuditResult(@PathVariable("miniappId")String miniappId) throws Exception {
		return releaseLogService.getAuditResult(miniappId);
	}

	@PostMapping(value = "/release/{releaseLogId}")
	@ApiOperation("发布")
	public void release(@PathVariable("releaseLogId") Long releaseLogId) {
		releaseLogService.release(releaseLogId);
	}

	@PostMapping("/bitch_commit/{templateId}/{releaseAfterAudit}")
	@ApiOperation("批量体提交小程序代码审核")
	public List<String> bitchCommit(@PathVariable("templateId")String templateId,
									@PathVariable("releaseAfterAudit") String releaseAfterAudit) {
		YesOrNoEnum yesOrNoEnum = YesOrNoEnum.no;
		if("yes".equals(releaseAfterAudit)) {
			yesOrNoEnum = YesOrNoEnum.yes;
		}
		return releaseLogService.bitchCommit(templateId, yesOrNoEnum, checkList);
	}

	@PostMapping("/bitch_release/{templateId}")
	@ApiOperation("批量发布小程序")
	public List<String> bitchRelease(@PathVariable("templateId")String templateId) {
		return releaseLogService.bitchRelease(templateId);
	}

	@GetMapping("/pager")
	@ApiOperation("获取小程序发布记录分页列表")
	public ShowPageImpl<MiniappReleaseLogPagerDTO> getPager(@RequestParam("page") Integer page,
															@RequestParam("size") Integer size,
															@RequestParam(value = "miniappId", required = false) String miniappId,
															@RequestParam(value = "templateId", required = false) String templateId) {
		return releaseLogService.getPager(page, size, miniappId, templateId);
	}

	@GetMapping("/detail/{logId}")
	@ApiOperation("获取小程序发布记录详情")
	public MiniappReleaseLogDetail getDetail(@PathVariable("logId") Long logId) {
		return releaseLogService.getDetail(logId);
	}

}
