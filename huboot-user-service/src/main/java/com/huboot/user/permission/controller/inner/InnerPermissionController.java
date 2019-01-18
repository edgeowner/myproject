package com.huboot.user.permission.controller.inner;

import com.huboot.commons.sso.PermissionResourcesDTO;
import com.huboot.share.user_service.api.feign.PermissionFeignClient;
import com.huboot.user.permission.service.IPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

import java.util.List;

@Api(tags = "内部端-用户服务-权限表 API")
@RestController
public class InnerPermissionController implements PermissionFeignClient {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IPermissionService permissionService;

	@Override
	public List<PermissionResourcesDTO> getRolePermission(Long roleId) {
		return permissionService.getRolePermission(roleId);
	}
}
