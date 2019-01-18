package com.huboot.user.user.controller.zkshop;

import com.huboot.commons.page.ShowPageImpl;
import com.huboot.user.user.dto.zkshop.ZkshopUserEmployeeQueryReqDTO;
import com.huboot.user.user.dto.zkshop.ZkshopUserEmployeeCreateReqDTO;
import com.huboot.user.user.dto.zkshop.ZkshopUserEmployeeModifyReqDTO;
import com.huboot.user.user.dto.zkshop.ZkshopUserEmployeeDetailResDTO;
import com.huboot.user.user.dto.zkshop.ZkshopUserEmployeePageResDTO;
import com.huboot.user.user.service.IUserEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "直客商家端-用户服务-企业员工表 API")
@RestController
@RequestMapping(value = "/zkshop/user/userEmployee")
public class ZkshopUserEmployeeController {

	@Autowired
	private IUserEmployeeService userEmployeeService;

	@PostMapping(value = "")
	@ApiOperation("创建")
	public void create(@RequestBody @Valid ZkshopUserEmployeeCreateReqDTO dto) {
		userEmployeeService.create(dto);
	}

    @PostMapping(value = "/{id}")
	@ApiOperation("更新")
    public void modify(@PathVariable("id") Long id, @RequestBody @Valid ZkshopUserEmployeeModifyReqDTO dto) {
		dto.setId(id);
		userEmployeeService.modify(dto);
    }

	@DeleteMapping(value = "/{id}")
	@ApiOperation("删除")
	public void delete(@PathVariable("id") Long id) {
	userEmployeeService.delete(id);
	}

    @GetMapping(value = "/{id}")
	@ApiOperation("查询")
    public ZkshopUserEmployeeDetailResDTO get(@PathVariable("id") Long id) {
		return userEmployeeService.find(id);
    }

	@GetMapping(value = "/page")
	@ApiOperation("分页")
    public ShowPageImpl<ZkshopUserEmployeePageResDTO> findPage(ZkshopUserEmployeeQueryReqDTO queryReqDTO) {
    	return userEmployeeService.findPage(queryReqDTO);
    }
}
