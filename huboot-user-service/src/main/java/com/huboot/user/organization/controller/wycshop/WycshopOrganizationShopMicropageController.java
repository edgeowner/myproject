package com.huboot.user.organization.controller.wycshop;

import com.huboot.user.organization.dto.*;
import com.huboot.user.organization.service.IOrganizationShopMicropageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "网约车管理端-组织-微页面表 API")
@RestController
@RequestMapping(value = "/wycshop/organization/organizationShopMicropage")
public class WycshopOrganizationShopMicropageController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IOrganizationShopMicropageService organizationShopMicropageService;

	@GetMapping(value = "/promotion/detail")
	@ApiOperation("微页面推广详情")
	public OrganizationShopMicropagePromotionDTO promotionDetail() {
		return organizationShopMicropageService.promotionDetail(null);
	}

	@GetMapping(value = "/agent/detail")
	@ApiOperation("经纪人推广详情")
	public OrganizationShopMicropageAgentDTO agentDetail() {
		return organizationShopMicropageService.agentDetail(null);
	}

	@GetMapping(value = "/introduction/detail")
	@ApiOperation("企业介绍详情")
	public OrganizationShopMicropageIntroductionDTO introductionDetail() {
		return organizationShopMicropageService.introductionDetail(null);
	}

	@GetMapping(value = "/welfare/detail")
	@ApiOperation("司机福利详情")
	public OrganizationShopMicropageWelfareDTO welfareDetail() {
		return organizationShopMicropageService.welfareDetail(null);
	}

	@GetMapping(value = "/model/detail")
	@ApiOperation("车型详情")
	public List<OrganizationShopMicropageModelDTO> modelDetail() {
		return organizationShopMicropageService.modelDetail(null);
	}

	@GetMapping(value = "/guide/detail")
	@ApiOperation("入行指南详情")
	public OrganizationShopMicropageGuideDTO guideDetail() {
		return organizationShopMicropageService.guideDetail(null);
	}

	@GetMapping(value = "/contact/detail")
	@ApiOperation("联系方式详情")
	public OrganizationShopMicropageContactDTO contactDetail() {
		return organizationShopMicropageService.contactDetail(null);
	}


	@PostMapping(value = "/promotion/save")
	@ApiOperation("微页面推广保存")
	public void promotionSave(@RequestBody @Valid OrganizationShopMicropagePromotionDTO dto) {
		organizationShopMicropageService.promotionSaveOrPublish(dto, 0);
	}

	@PostMapping(value = "/promotion/publish")
	@ApiOperation("微页面推广发布")
	public void promotionPublish(@RequestBody @Valid OrganizationShopMicropagePromotionDTO dto) {
		organizationShopMicropageService.promotionSaveOrPublish(dto, 1);
	}

	@PostMapping(value = "/agent/save")
	@ApiOperation("经纪人推广保存")
	public void promotionSave(@RequestBody @Valid OrganizationShopMicropageAgentDTO dto) {
		organizationShopMicropageService.agentSaveOrPublish(dto, 0);
	}

	@PostMapping(value = "/agent/publish")
	@ApiOperation("经纪人推广发布")
	public void promotionPublish(@RequestBody @Valid OrganizationShopMicropageAgentDTO dto) {
		organizationShopMicropageService.agentSaveOrPublish(dto, 1);
	}

	@PostMapping(value = "/introduction/save")
	@ApiOperation("企业介绍保存")
	public void promotionSave(@RequestBody @Valid OrganizationShopMicropageIntroductionDTO dto) {
		organizationShopMicropageService.introductionSaveOrPublish(dto, 0);
	}

	@PostMapping(value = "/introduction/publish")
	@ApiOperation("企业介绍发布")
	public void promotionPublish(@RequestBody @Valid OrganizationShopMicropageIntroductionDTO dto) {
		organizationShopMicropageService.introductionSaveOrPublish(dto, 1);
	}

	@PostMapping(value = "/welfare/save")
	@ApiOperation("司机福利保存")
	public void promotionSave(@RequestBody @Valid OrganizationShopMicropageWelfareDTO dto) {
		organizationShopMicropageService.welfareSaveOrPublish(dto, 0);
	}

	@PostMapping(value = "/welfare/publish")
	@ApiOperation("司机福利发布")
	public void promotionPublish(@RequestBody @Valid OrganizationShopMicropageWelfareDTO dto) {
		organizationShopMicropageService.welfareSaveOrPublish(dto, 1);
	}

	@PostMapping(value = "/model/save")
	@ApiOperation("车型详情保存")
	public void modelSave(@RequestBody @Valid List<OrganizationShopMicropageModelDTO> dtoList) {
		organizationShopMicropageService.modelSaveOrPublish(dtoList, 0);
	}

	@PostMapping(value = "/model/publish")
	@ApiOperation("车型详情发布")
	public void modelPublish(@RequestBody @Valid List<OrganizationShopMicropageModelDTO> dtoList) {
		organizationShopMicropageService.modelSaveOrPublish(dtoList, 1);
	}

	@PostMapping(value = "/guide/save")
	@ApiOperation("入行指南保存")
	public void guideSave(@RequestBody @Valid OrganizationShopMicropageGuideDTO dto) {
		organizationShopMicropageService.guideSaveOrPublish(dto, 0);
	}

	@PostMapping(value = "/guide/publish")
	@ApiOperation("入行指南发布")
	public void guidePublish(@RequestBody @Valid OrganizationShopMicropageGuideDTO dto) {
		organizationShopMicropageService.guideSaveOrPublish(dto, 1);
	}

	@PostMapping(value = "/contact/save")
	@ApiOperation("联系方式保存")
	public void contactSave(@RequestBody @Valid OrganizationShopMicropageContactDTO dto) {
		organizationShopMicropageService.contactSaveOrPublish(dto, 0);
	}

	@PostMapping(value = "/contact/publish")
	@ApiOperation("联系方式发布")
	public void contactPublish(@RequestBody @Valid OrganizationShopMicropageContactDTO dto) {
		organizationShopMicropageService.contactSaveOrPublish(dto, 1);
	}
}
