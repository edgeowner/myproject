package com.huboot.user.auth.dto.zkuser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户中心-用户主表
 */
@Data
public class UserCustomerWeixinPhoneNumberAddReqDTO implements Serializable {

    @ApiModelProperty("包括敏感数据在内的完整用户信息的加密数据")
    @NotBlank(message = "包括敏感数据在内的完整用户信息的加密数据不能为空")
    private String encryptedData;
    @ApiModelProperty("加密算法的初始向量")
    @NotBlank(message = "加密算法的初始向量不能为空")
    private String iv;
    @ApiModelProperty("code")
    @NotBlank(message = "code不能为空")
    private String code;
}

