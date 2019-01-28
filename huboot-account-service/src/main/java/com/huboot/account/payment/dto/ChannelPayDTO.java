package com.huboot.account.payment.dto;

import com.huboot.account.payment.entity.PaymentSequenceEntity;
import lombok.Data;

import java.util.Map;

/**
 * Created by Administrator on 2018/8/31 0031.
 */
@Data
public class ChannelPayDTO {

    private PaymentSequenceEntity sequenceEntity;

    private Map<String, Object> extendMap;
}
