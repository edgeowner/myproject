package com.huboot.business.base_model.ali_service.dto;

import com.huboot.business.base_model.login.sso.client.utils.WebUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/***
 * 发送验证码DTO
 * **/
@ApiModel(value="发送校验验证码DTO",description="SendMsgCodeReq")
@Data
public class SendMsgCodeReq implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="手机号码",required=true)
    @Pattern(regexp= WebUtil.EXP_MOBILE_PHONE,message="手机号格式错误",groups = {ISendMsg.class,ValidateMsg.class})
    private String phone;

    @ApiModelProperty(value="验证码")
    @NotEmpty(message = "验证码不能为空",groups = {ValidateMsg.class})
    private String code;
}
