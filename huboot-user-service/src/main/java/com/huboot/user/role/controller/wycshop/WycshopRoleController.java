package com.huboot.user.role.controller.wycshop;

import com.huboot.commons.page.ShowPageImpl;
import com.huboot.user.role.dto.RoleDetailResDTO;
import com.huboot.user.role.dto.wycshop.WycShopRoleCreateReqDTO;
import com.huboot.user.role.dto.RoleModifyReqDTO;
import com.huboot.user.role.dto.wycshop.WycShopRolePageResDTO;
import com.huboot.user.role.dto.wycshop.WycShopRoleQueryReqDTO;
import com.huboot.user.role.service.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "网约车管理端-用户服务-角色表 API")
@RestController
@RequestMapping(value = "/wycshop/role/role")
public class WycshopRoleController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IRoleService roleService;

    @PostMapping(value = "")
    @ApiOperation("创建")
    public void create(@RequestBody @Valid WycShopRoleCreateReqDTO dto) {
        roleService.create(dto);
    }

    @PatchMapping(value = "/{id}")
    @ApiOperation("更新")
    public void modify(@PathVariable("id") Long id, @RequestBody @Valid RoleModifyReqDTO dto) {
        dto.setId(id);
        roleService.modify(dto);
    }

	@DeleteMapping(value = "/{id}")
	@ApiOperation("删除")
	public void delete(@PathVariable("id") Long id) {
	roleService.delete(id);
	}

    @GetMapping(value = "/{id}")
    @ApiOperation("查询")
    public RoleDetailResDTO get(@PathVariable("id") Long id) {
        RoleDetailResDTO dto = roleService.find(id);
        return dto;
    }

    @GetMapping(value = "")
    @ApiOperation("查询列表")
    public List<RoleDetailResDTO> getList() {
        return roleService.findAll();
    }

    @GetMapping(value = "/page")
    @ApiOperation("分页")
    public ShowPageImpl<WycShopRolePageResDTO> findPageForWycShop(WycShopRoleQueryReqDTO queryReqDTO) {
        ShowPageImpl<WycShopRolePageResDTO> page = roleService.findPageForWycShop(queryReqDTO);
        return page;
    }
}
