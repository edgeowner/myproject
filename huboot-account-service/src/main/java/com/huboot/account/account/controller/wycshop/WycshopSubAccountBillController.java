package com.huboot.account.account.controller.wycshop;

import com.huboot.account.account.dto.wycshop.SubAccountBillDetailPagerDTO;
import com.huboot.account.account.service.ISubAccountBillQueryService;
import com.huboot.commons.page.ShowPageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "网约车管理端- API")
@RestController
@RequestMapping(value = "/wycshop/account/sub_account_bill")
public class WycshopSubAccountBillController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ISubAccountBillQueryService subAccountBillQueryService;

	@GetMapping(value = "/get_sub_account_bill_pager")
	@ApiOperation("查询个人子账户流水")
	public ShowPageImpl<SubAccountBillDetailPagerDTO> getPersionSubAccountBillPager(@RequestParam(name = "page", defaultValue = "1") Integer page,
																					@RequestParam(name = "size", defaultValue = "10") Integer size,
																					@RequestParam(name = "userId") String userId,
																					@RequestParam(name = "tripartiteSeq", required = false) String tripartiteSeq,
																					@RequestParam(name = "orderSource", required = false) String orderSource,
																					@RequestParam(name = "startTime", required = false) String startTime,
																					@RequestParam(name = "endTime", required = false) String endTime) {
		return subAccountBillQueryService.getWycBrokerageSubAccountBillPager(userId, tripartiteSeq, orderSource, startTime, endTime, page, size);
	}

}
