package com.huboot.account.account.controller.admin;

import com.huboot.account.account.service.ISubAccountBillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

@Api(tags = "内管端- API")
@RestController
@RequestMapping(value = "/admin/account/subAccountBill")
public class AdminSubAccountBillController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ISubAccountBillService subAccountBillService;

}
