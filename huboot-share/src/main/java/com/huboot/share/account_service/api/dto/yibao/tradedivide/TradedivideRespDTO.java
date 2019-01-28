package com.huboot.share.account_service.api.dto.yibao.tradedivide;

import lombok.Data;

import java.io.Serializable;

@Data
public class TradedivideRespDTO implements Serializable {

    private String code;//	code	STRING

    private String merchant_no;//	merchant_no	STRING

    private String parent_merchant_no;//	parent_merchant_no	STRING

    private String message;//	message	STRING

    private String  biz_system_no;//	biz_system_no	STRING

    private String order_id;//	order_id	STRING

    private String  unique_order_no;//	unique_order_no	STRING

    private String divide_request_id;//	divide_request_id	STRING

    private String status;//	status	STRING

    private String divide_detail;//	divide_detail	STRING

}
