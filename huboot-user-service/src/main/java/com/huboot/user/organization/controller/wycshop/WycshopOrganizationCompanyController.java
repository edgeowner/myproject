package com.huboot.user.organization.controller.wycshop;

import com.huboot.commons.page.ShowPageImpl;
import com.huboot.user.organization.dto.admin.OrganizationCompanyCreateReqDTO;
import com.huboot.user.organization.dto.admin.OrganizationCompanyDetailResDTO;
import com.huboot.user.organization.dto.admin.OrganizationCompanyModifyReqDTO;
import com.huboot.user.organization.dto.admin.OrganizationCompanyPageResDTO;
import com.huboot.user.organization.dto.admin.OrganizationCompanyQueryReqDTO;
import com.huboot.user.organization.service.IOrganizationCompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "网约车管理端-用户服务-公司表 API")
@RestController
@RequestMapping(value = "/wycshop/organization/organizationCompany")
public class WycshopOrganizationCompanyController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IOrganizationCompanyService organizationCompanyService;

	@PostMapping(value = "")
	@ApiOperation("创建")
	public void create(@RequestBody OrganizationCompanyCreateReqDTO dto) {
		organizationCompanyService.create(dto);
	}

    @PatchMapping(value = "/{id}")
	@ApiOperation("更新")
    public void modify(@PathVariable("id") Long id, @RequestBody OrganizationCompanyModifyReqDTO dto) {
		dto.setId(id);
		organizationCompanyService.modify(dto);
    }

	@DeleteMapping(value = "/{id}")
	@ApiOperation("删除")
	public void delete(@PathVariable("id") Long id) {
	organizationCompanyService.delete(id);
	}

    @GetMapping(value = "/{id}")
	@ApiOperation("查询")
    public OrganizationCompanyDetailResDTO get(@PathVariable("id") Long id) {
		OrganizationCompanyDetailResDTO dto = organizationCompanyService.find(id);
		return dto;
    }

	@GetMapping(value = "/page")
	@ApiOperation("分页")
    public ShowPageImpl<OrganizationCompanyPageResDTO> findPage(OrganizationCompanyQueryReqDTO queryReqDTO) {
    	ShowPageImpl<OrganizationCompanyPageResDTO> page = organizationCompanyService.findPage(queryReqDTO);
    	return page;
    }
}
