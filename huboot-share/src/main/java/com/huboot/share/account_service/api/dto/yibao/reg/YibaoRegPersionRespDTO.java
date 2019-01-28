package com.huboot.share.account_service.api.dto.yibao.reg;

import lombok.Data;

import java.io.Serializable;

@Data
public class YibaoRegPersionRespDTO extends YibaoBaseRespDTO implements Serializable {

    private String agreementContent;//协议内容
}
