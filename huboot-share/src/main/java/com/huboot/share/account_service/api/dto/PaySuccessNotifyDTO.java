package com.huboot.share.account_service.api.dto;

import com.huboot.share.account_service.api.dto.order_payment.CreateOPItemReqDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/1/8 0008.
 */
@Data
public class PaySuccessNotifyDTO {

    private String paySn;

    private Long payUserId;

    private LocalDateTime payTime;

    @ApiModelProperty("支付订单条目")
    private List<CreateOPItemReqDTO> items = new ArrayList<>();

    public void addItem(CreateOPItemReqDTO reqDTO) {
        this.items.add(reqDTO);
    }
}
