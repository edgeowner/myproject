package com.huboot.share.file_service.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AgentShareDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @ApiModelProperty("图片名称")
    private String name ;
    @NotNull
    @ApiModelProperty("二维码路径")
    private String qrcodePath;

    public AgentShareDTO() {
    }

    public AgentShareDTO(@NotNull String name, @NotNull String qrcodePath) {
        this.name = name;
        this.qrcodePath = qrcodePath;
    }
}
