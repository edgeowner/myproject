package com.huboot.account.account.controller.wycminiapp;

import com.huboot.account.account.service.ISubAccountService;
import com.huboot.account.account.dto.wycminiapp.SubAccountDetailDTO;
import com.huboot.commons.filter.RequestInfo;
import com.huboot.commons.utils.BigDecimalUtil;
import com.huboot.share.account_service.api.dto.SubAccountDTO;
import com.huboot.share.account_service.enums.SubAccountTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/9/3 0003.
 */
@Api(tags = "网约车司机小程序端子账户- API")
@RestController
@RequestMapping(value = "/wycdriverminiapp/account/sub_account")
public class WycminiappSubAccountController {

    @Autowired
    private ISubAccountService subAccountService;

    @GetMapping(value = "/get_persion_sub_account")
    @ApiOperation("查询个人子账户")
    public SubAccountDetailDTO getPersionSubAccount() {
        SubAccountDTO dto = subAccountService.getSubAccount(RequestInfo.getJwtUser().getUserId().toString(), SubAccountTypeEnum.brokerage);
        SubAccountDetailDTO detailDTO = new SubAccountDetailDTO();
        detailDTO.setType(dto.getType());
        detailDTO.setStatus(dto.getStatus());
        detailDTO.setTotalBalance(BigDecimalUtil.amountShow(dto.getTotalBalance()));
        detailDTO.setUnusableBalance(BigDecimalUtil.amountShow(dto.getUnusableBalance()));
        detailDTO.setUsableBalance(BigDecimalUtil.amountShow(dto.getUsableBalance()));
        return detailDTO;
    }

}
