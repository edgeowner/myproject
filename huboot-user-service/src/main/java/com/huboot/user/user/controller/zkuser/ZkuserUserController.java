package com.huboot.user.user.controller.zkuser;

import com.huboot.commons.page.ShowPageImpl;
import com.huboot.user.user.dto.UserCreateReqDTO;
import com.huboot.user.user.dto.UserQueryReqDTO;
import com.huboot.user.user.dto.UserModifyReqDTO;
import com.huboot.user.user.dto.UserPageResDTO;
import com.huboot.user.user.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;

@Api(tags = "直客用户端-用户中心-用户基础信息表 API")
@RestController
@RequestMapping(value = "/zkuser/user/user")
public class ZkuserUserController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IUserService userService;

	@PostMapping(value = "")
	@ApiOperation("创建")
	public void create(@RequestBody @Valid UserCreateReqDTO dto) {
//		userService.create(dto);
	}

    @PatchMapping(value = "/{id}")
	@ApiOperation("更新")
    public void modify(@PathVariable("id") Long id, @RequestBody @Valid UserModifyReqDTO dto) {
		dto.setId(id);
		userService.modify(dto);
    }

	@DeleteMapping(value = "/{id}")
	@ApiOperation("删除")
	public void delete(@PathVariable("id") Long id) {
	userService.delete(id);
	}

	@GetMapping(value = "/page")
	@ApiOperation("分页")
    public ShowPageImpl<UserPageResDTO> findPage(UserQueryReqDTO queryReqDTO) {
    	return userService.findPage(queryReqDTO);
    }
}
