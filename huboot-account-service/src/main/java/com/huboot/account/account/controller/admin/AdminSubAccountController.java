package com.huboot.account.account.controller.admin;

import com.huboot.account.account.service.ISubAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

@Api(tags = "内管端-子账户 API")
@RestController
@RequestMapping(value = "/admin/account/sub_account")
public class AdminSubAccountController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ISubAccountService subAccountService;

	@PostMapping("/exchenge_account_relaid")
	public void exchengeRelaId() {
		subAccountService.exchengeRelaId();
	}
}
