package com.huboot.business.base_model.pay.dto.account;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/5/10 0010.
 */
@Data
public class ZKLedgerSyncDTO implements Serializable {

    private Long detailId;

    private String orderSn;

    private String paymentSn;

    private Integer paymentType;

    private Integer detailStatus;

    private Date paymentTime;

    private BigDecimal amount;

    private String detailSn;

    private Long relaDetailId;

    private List<PaymentItemDTO> itemList;

}
