package com.huboot.user.organization.controller.zkshop;

import com.huboot.commons.page.ShowPageImpl;
import com.huboot.user.organization.dto.zkshop.*;
import com.huboot.user.organization.service.IOrganizationShopStoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Api(tags = "直客商家端-组织-店铺门店表 API")
@RestController
@RequestMapping(value = "/zkshop/organization/organizationShopStore")
public class ZkshopOrganizationShopStoreController {

    @Autowired
    private IOrganizationShopStoreService organizationShopStoreService;

    @PostMapping(value = "")
    @ApiOperation("创建")
    public void create(@RequestBody @Valid ZkshopOrganizationShopStoreCreateReqDTO dto) {
        organizationShopStoreService.create(dto);
    }

    @PostMapping(value = "/{id}")
    @ApiOperation("更新")
    public void modify(@PathVariable("id") Long id, @RequestBody @Valid ZkshopOrganizationShopStoreModifyReqDTO dto) {
        dto.setId(id);
        organizationShopStoreService.modify(dto);
    }

    @PostMapping(value = "/{id}/defaultStatus")
    @ApiOperation("更新默认:选中动作")
    public void modifyDefaultStatus(@PathVariable("id") Long id) {
        organizationShopStoreService.modifyDefaultStatus(id);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation("删除")
    public void delete(@PathVariable("id") Long id) {
        organizationShopStoreService.delete(id);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation("查询")
    public ZkshopOrganizationShopStoreDetailResDTO get(@PathVariable("id") Long id) {
        return organizationShopStoreService.find(id);
    }

    @GetMapping(value = "/page")
    @ApiOperation("分页")
    public ShowPageImpl<ZkshopOrganizationShopStorePageResDTO> findPage(ZkshopOrganizationShopStoreQueryReqDTO queryReqDTO) {
        return organizationShopStoreService.findPage(queryReqDTO);
    }
}
