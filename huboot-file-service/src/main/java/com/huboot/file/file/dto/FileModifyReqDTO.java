package com.huboot.file.file.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *文件服务-文件信息表
 */
@Data
public class FileModifyReqDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "唯一标识", hidden = true)
	private Long id;
	@ApiModelProperty("名称")
	private String name;
	@ApiModelProperty("路径")
	private String path;
	@ApiModelProperty("扩展名")
	private String ext;
	@ApiModelProperty("大小")
	private String fileSize;
	@ApiModelProperty("全路径")
	private String fullPath;

}

