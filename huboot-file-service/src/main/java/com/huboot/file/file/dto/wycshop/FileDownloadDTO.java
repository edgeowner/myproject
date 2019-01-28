package com.huboot.file.file.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 文件服务-文件信息表
 */
@Data
public class FileDownloadDTO implements Serializable {

    @ApiModelProperty("目标url")
    @NotBlank(message = "目标url不能为空")
    private String targetUrl;

}

