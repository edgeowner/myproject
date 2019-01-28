package com.huboot.share.account_service.api.feign;

import com.huboot.share.account_service.api.dto.BrokerageDetail;
import com.huboot.share.account_service.api.dto.SubAccountCreateReqDTO;
import com.huboot.share.account_service.api.dto.SubAccountDTO;
import com.huboot.share.account_service.api.fallback.SubAccountFallback;
import com.huboot.share.common.constant.ServiceName;
import com.huboot.share.common.feign.FeignCilentConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Administrator on 2018/8/31 0031.
 */
@FeignClient(name = ServiceName.ACCOUNT_SERVICE, configuration = FeignCilentConfig.class, fallback = SubAccountFallback.class)
public interface SubAccountFeignClient {

    /**
     * 创建账户
     */
    @PostMapping("/inner/account/sub_account/create_sub_account")
    Long createSubAccount(@Validated @RequestBody SubAccountCreateReqDTO reqDTO);

    @GetMapping("/inner/account/sub_account/{subAccountType}/{relaId}")
    SubAccountDTO getByRelaId(@PathVariable("relaId") String relaId, @PathVariable("subAccountType") String subAccountType);

    @GetMapping("/inner/account/sub_account/get_brokerage_detail/{relaId}")
    BrokerageDetail getBrokerageDetail(@PathVariable("relaId") String relaId);
}
