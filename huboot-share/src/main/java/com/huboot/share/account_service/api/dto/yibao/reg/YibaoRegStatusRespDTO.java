package com.huboot.share.account_service.api.dto.yibao.reg;

import lombok.Data;

import java.io.Serializable;

@Data
public class YibaoRegStatusRespDTO extends YibaoBaseRespDTO implements Serializable {

    public static final String STATUS_SUCCESS = "PROCESS_SUCCESS";

    /**
     * 待审核=INIT；
     *
     * 审核中=PROCESSING；
     *
     * 审核通过=PROCESS_SUCCESS；
     *
     * 审核拒绝=PROCESS_REJECT；
     *
     * 审核回退=PROCESS_BACK；
     *
     * 产品提前开通 =PROCESSING_PRODUCT_INFO _ SUCCESS；
     * **/
    private String merNetInOutStatus;

}
