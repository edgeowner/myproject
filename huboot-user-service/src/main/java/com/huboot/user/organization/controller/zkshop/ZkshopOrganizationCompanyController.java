package com.huboot.user.organization.controller.zkshop;

import com.huboot.user.organization.dto.zkshop.OrganizationCompanyCreateForZkShopReqDTO;
import com.huboot.user.organization.service.IOrganizationCompanyService;
import com.huboot.user.user.dto.zkshop.BusinessLicenseORCDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "直客商家端-用户服务-公司表 API")
@RestController
@RequestMapping(value = "/zkshop/organization/organizationCompany")
@Slf4j
public class ZkshopOrganizationCompanyController {

    @Autowired
    private IOrganizationCompanyService organizationCompanyService;

    @PostMapping("/businessLicenseOrc")
    @ApiOperation("营业执照识别")
    public BusinessLicenseORCDTO businessLicenseOrc(@RequestParam("file") MultipartFile file) throws Exception {
        return organizationCompanyService.businessLicenseOrc(file);
    }

    @PostMapping(value = "")
    @ApiOperation("创建")
    public void create(@RequestBody OrganizationCompanyCreateForZkShopReqDTO dto) {
        organizationCompanyService.create(dto);
    }

    /*@GetMapping(value = "/{id}")
    @ApiOperation("查询")
    public OrganizationCompanyDetailForZkShopResDTO get(@PathVariable("id") Long id) {
        OrganizationCompanyDetailForZkShopResDTO dto = organizationCompanyService.findForZkShop(id);
        return dto;
    }*/

}
