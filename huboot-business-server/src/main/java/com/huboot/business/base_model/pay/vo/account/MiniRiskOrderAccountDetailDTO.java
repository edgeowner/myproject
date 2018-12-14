package com.huboot.business.base_model.pay.vo.account;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/8/24 0024.
 */
@Data
public class MiniRiskOrderAccountDetailDTO {

    private String userId;

    private String tracedSn;

    private BigDecimal price;

}
