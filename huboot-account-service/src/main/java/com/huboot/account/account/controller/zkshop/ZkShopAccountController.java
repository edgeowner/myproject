package com.huboot.account.account.controller.zkshop;

import com.huboot.account.account.service.ISubAccountService;
import com.huboot.share.account_service.api.dto.yibao.reg.YibaoRegStatusRespDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
public class ZkShopAccountController {

    @Autowired
    private ISubAccountService subAccountService;

    /**
     * 创建易宝分账账户（商户在易宝平台分润结算账户）
     * @return merchantNo
     */
    @PostMapping("/zkshop/account/sub_account/create_yibao_account")
    public String createSettlementAccount(@RequestBody Map<String,String> reqDTO) throws IOException{
        return subAccountService.createSettlementAccount(reqDTO);
    }

    /**
     * 入网状态查询
     * @return merchantNo
     */
    @GetMapping("/zkshop/account/sub_account/query_yibao_account")
    public YibaoRegStatusRespDTO querySettlementAccount(@RequestParam Long accountId) throws IOException{
        return subAccountService.querySettlementAccount(accountId);
    }


}
