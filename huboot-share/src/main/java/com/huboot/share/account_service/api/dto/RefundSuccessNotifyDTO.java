package com.huboot.share.account_service.api.dto;

import com.huboot.share.account_service.api.dto.order_payment.CreateOPItemReqDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Administrator on 2019/1/8 0008.
 */
@Data
public class RefundSuccessNotifyDTO {

    private String paySn;

    //
    private LocalDateTime payTime;

    @ApiModelProperty("支付订单条目")
    private List<CreateOPItemReqDTO> items;
}
