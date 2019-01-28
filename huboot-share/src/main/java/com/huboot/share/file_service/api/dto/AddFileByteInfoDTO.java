package com.huboot.share.file_service.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AddFileByteInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文件名称,不含后缀")
    @NotNull
    private String name ;
    @ApiModelProperty("扩展名")
    @NotNull
    private String ext ;
    @ApiModelProperty("文件的字节数组")
    @NotNull
    private byte[] dataBytes;
}
