package com.huboot.share.account_service.api.fallback;

import com.huboot.commons.component.exception.BizException;
import com.huboot.share.account_service.api.dto.BrokerageDetail;
import com.huboot.share.account_service.api.dto.SubAccountCreateReqDTO;
import com.huboot.share.account_service.api.dto.SubAccountDTO;
import com.huboot.share.account_service.api.feign.SubAccountFeignClient;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Administrator on 2018/8/31 0031.
 */
@Component
public class SubAccountFallback implements SubAccountFeignClient {

    @Override
    public Long createSubAccount(@Validated @RequestBody SubAccountCreateReqDTO reqDTO) {
        throw new BizException("请求失败");
    }

    @Override
    public SubAccountDTO getByRelaId(@PathVariable("relaId") String relaId, @PathVariable("subAccountType") String subAccountType) {
        return null;
    }

    @Override
    public BrokerageDetail getBrokerageDetail(@PathVariable("relaId") String relaId) {
        return new BrokerageDetail();
    }


}
