package com.huboot.file.file.dto.inner;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.File;
import java.io.Serializable;

/**
 * 文件服务-文件信息表
 */
@Data
public class FileCreateReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("文件对象")
    private File file;
}

