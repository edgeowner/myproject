package com.huboot.account.account.controller.wycshop;

import com.huboot.account.account.dto.wycshop.BrokerageAccountDetailDTO;
import com.huboot.account.account.dto.wycshop.SubAccountPagerDTO;
import com.huboot.account.account.service.ISubAccountService;
import com.huboot.commons.page.ShowPageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "网约车管理端-子账户 API")
@RestController
@RequestMapping(value = "/wycshop/account/sub_account")
public class WycshopSubAccountController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ISubAccountService subAccountService;

	@GetMapping(value = "/get_brokerage_account_pager")
	@ApiOperation("查询个人子账户列表")
	public ShowPageImpl<SubAccountPagerDTO> getPersionSubAccountPager(@RequestParam(name = "page", defaultValue = "1") Integer page,
																	  @RequestParam(name = "size", defaultValue = "10") Integer size,
																	  @RequestParam(name = "name", required = false) String name,
																	  @RequestParam(name = "phone", required = false) String phone,
																	  @RequestParam(name = "idcar", required = false) String idcar) {
		return subAccountService.getWycBrokerageSubAccountPager(name, phone, idcar, page, size);
	}

	@GetMapping(value = "/get_brokerage_account_detail/{userId}")
	public BrokerageAccountDetailDTO getBrokerageAccountDetail(@PathVariable("userId")Long userId) {
		return subAccountService.getBrokerageAccountDetail(userId);
	}

}
