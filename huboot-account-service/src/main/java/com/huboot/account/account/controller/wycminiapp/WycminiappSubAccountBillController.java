package com.huboot.account.account.controller.wycminiapp;

import com.huboot.account.account.dto.SubAccountBillPagerDTO;
import com.huboot.account.account.dto.wycminiapp.SubAccountBillDetailDTO;
import com.huboot.account.account.service.ISubAccountBillQueryService;
import com.huboot.commons.filter.RequestInfo;
import com.huboot.commons.page.ShowPageImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2018/9/3 0003.
 */
@Api(tags = "网约车司机小程序端账单- API")
@RestController
@RequestMapping(value = "/wycdriverminiapp/account/sub_account_bill")
public class WycminiappSubAccountBillController {

    @Autowired
    private ISubAccountBillQueryService subAccountBillQueryService;

    @GetMapping(value = "/get_sub_account_bill_pager")
    @ApiOperation("查询个人账单列表")
    public ShowPageImpl<SubAccountBillPagerDTO> getPersionSubAccountBillPager(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                                              @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                                              @RequestParam(name = "type", defaultValue = "all") String type) {
        return subAccountBillQueryService.getWycBrokerageSubAccountBillPager(RequestInfo.getJwtUser().getUserId().toString(),
                type, page, size);
    }

    @GetMapping(value = "/get_sub_account_bill_detail/{billId}")
    @ApiOperation("查询个人账单详情")
    public SubAccountBillDetailDTO getBillDetail(@PathVariable("billId")Long billId) {
        return subAccountBillQueryService.getWycBrokerageSubAccountBillDetail(RequestInfo.getJwtUser().getUserId().toString(), billId);
    }

}
