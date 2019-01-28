package com.huboot.share.account_service.api.dto.yibao.reg;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class RegPersionDTO extends RegBasePersionDTO implements Serializable {

    //入网请求号, 字母加字符串组合， 不要带特殊符号
    @NotBlank
    private String requestNo;

    //系统商的商户编号
    @NotBlank
    private String parentMerchantNo;


}
