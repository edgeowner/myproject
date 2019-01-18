package com.huboot.user.user.controller.admin;

import com.huboot.commons.page.ShowPageImpl;
import com.huboot.user.user.dto.admin.*;
import com.huboot.user.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "内管端-用户服务-用户基础信息表 API")
@RestController
@RequestMapping(value = "/admin/user/user")
public class AdminUserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserService userService;

    //===================================公司===================================/
    @PostMapping(value = "/organizationCompany")
    @ApiOperation("创建公司账号")
    public void create(@RequestBody UserCreateCompanyReqDTO dto) {
        userService.createOrganizationCompany(dto);
    }

    @PatchMapping(value = "/{id}/organizationCompany")
    @ApiOperation("更新公司账号")
    public void modify(@PathVariable("id") Long id, @RequestBody UserModifyCompanyReqDTO dto) {
        dto.setId(id);
        userService.modifyOrganizationCompany(dto);
    }

    @GetMapping(value = "/{id}/organizationCompany")
    @ApiOperation("查询公司账号")
    public UserDetailCompanyResDTO getOrganizationCompany(@PathVariable("id") Long id) {
        UserDetailCompanyResDTO dto = userService.findCompany(id);
        return dto;
    }

    @GetMapping(value = "/page/organizationCompany")
    @ApiOperation("分页公司账号")
    public ShowPageImpl<UserPageCompanyResDTO> findPage(UserQueryCompanyReqDTO queryReqDTO) {
        ShowPageImpl<UserPageCompanyResDTO> page = userService.findPageForOrganizationCompany(queryReqDTO);
        return page;
    }
    //===================================公司===================================/


    @PostMapping(value = "/all/redis")
    @ApiOperation("后门：刷新用户角色权限到redis，网关使用--手动执行")
    public void userInfoToRedis() {
        userService.userInfoToRedis();
    }

    @PostMapping(value = "/permission")
    @ApiOperation("后门：添加匿名角色的url资源")
    public void userInfoToRedis(PermissionCreateReqDTO wycShopRoleCreateReqDTO) {
        userService.createPermission(wycShopRoleCreateReqDTO);
    }

    //===================================内管===================================/
    @PostMapping(value = "/admin")
    @ApiOperation("创建内管账号")
    public void create(@RequestBody UserCreateAdminReqDTO dto) {
        userService.createAdmin(dto);
    }

    @PatchMapping(value = "/{id}/admin")
    @ApiOperation("更新内管账号")
    public void modify(@PathVariable("id") Long id, @RequestBody UserModifyAdminReqDTO dto) {
        dto.setId(id);
        userService.modifyAdmin(dto);
    }

    @GetMapping(value = "/{id}/admin")
    @ApiOperation("查询内管账号")
    public UserDetailAdminResDTO getAdmin(@PathVariable("id") Long id) {
        UserDetailAdminResDTO dto = userService.findAdmin(id);
        return dto;
    }

    @GetMapping(value = "/page/admin")
    @ApiOperation("分页内管账号")
    public ShowPageImpl<UserPageAdminResDTO> findPage(UserQueryAdminReqDTO queryReqDTO) {
        ShowPageImpl<UserPageAdminResDTO> page = userService.findPageForAdmin(queryReqDTO);
        return page;
    }
    //===================================内管===================================/

}
