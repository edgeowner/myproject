package com.huboot.account.notify.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class NotifyReqDTO implements Serializable {

    @NotBlank( message = "response不能为空")
    private String response;

    @NotBlank( message = "customerIdentification不能为空")
    private String customerIdentification;
}
