package com.huboot.user.role.controller.zkshop;

import com.huboot.commons.page.ShowPageImpl;
import com.huboot.user.role.dto.RoleDetailResDTO;
import com.huboot.user.role.dto.zkshop.ZkshopRoleQueryReqDTO;
import com.huboot.user.role.service.IRoleService;
import com.huboot.user.role.dto.zkshop.ZkshopRoleHoumenCreateReqDTO;
import com.huboot.user.role.dto.zkshop.ZkshopRoleModifyReqDTO;
import com.huboot.user.role.dto.zkshop.ZkshopRolePageResDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Api(tags = "直客商家端-用户服务-角色表 API")
@RestController
@RequestMapping(value = "/zkshop/role/role")
public class ZkshopRoleController {

    @Autowired
    private IRoleService roleService;

    @PostMapping(value = "/{id}")
    @ApiOperation("更新")
    public void modify(@PathVariable("id") Long id, @RequestBody @Valid ZkshopRoleModifyReqDTO dto) {
        dto.setId(id);
        roleService.modifyForZkshop(dto);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation("查询")
    public RoleDetailResDTO get(@PathVariable("id") Long id) {
        return roleService.find(id);
    }

    @PostMapping(value = "/organization/increment")
    @ApiOperation("后门--为指定公司,从现有角色模板创建角色")
    public void modify(@RequestBody @Valid ZkshopRoleHoumenCreateReqDTO dto) {
        roleService.incrementRoleForZkshop(dto);
    }

    @GetMapping(value = "/page")
    @ApiOperation("分页")
    public ShowPageImpl<ZkshopRolePageResDTO> findPage(ZkshopRoleQueryReqDTO queryReqDTO) {
        return roleService.findPageForZkShop(queryReqDTO);
    }
}
