package com.huboot.account.account.controller.inner;

import com.huboot.account.account.service.ISubAccountService;
import com.huboot.share.account_service.api.dto.BrokerageDetail;
import com.huboot.share.account_service.api.dto.SubAccountCreateReqDTO;
import com.huboot.share.account_service.api.dto.SubAccountDTO;
import com.huboot.share.account_service.api.feign.SubAccountFeignClient;
import com.huboot.share.account_service.enums.SubAccountTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "内部端-子账户 API")
@RestController
public class InnerSubAccountController implements SubAccountFeignClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ISubAccountService subAccountService;

    @Override
    @ApiOperation("创建子账户")
    public Long createSubAccount(@Validated @RequestBody SubAccountCreateReqDTO reqDTO) {
        return subAccountService.createSubAccount(reqDTO);
    }

    @Override
    @ApiOperation("获取子账户信息")
    public SubAccountDTO getByRelaId(@PathVariable("relaId") String relaId, @PathVariable("subAccountType") String subAccountType) {
        return subAccountService.getSubAccount(relaId, SubAccountTypeEnum.valueOf(subAccountType));
    }

    @Override
    @ApiOperation("获取佣金账户使用详情")
    public BrokerageDetail getBrokerageDetail(@PathVariable("relaId") String relaId) {
        return subAccountService.getBrokerageDetail(relaId);
    }


}
