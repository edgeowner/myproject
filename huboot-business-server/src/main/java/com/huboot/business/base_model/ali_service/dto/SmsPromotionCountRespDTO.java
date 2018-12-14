package com.huboot.business.base_model.ali_service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SmsPromotionCountRespDTO implements Serializable{

    private String shopUid;

    private Integer totalSend;

    private Integer totalSendSuccessed;


}
