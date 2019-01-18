package com.huboot.user.user.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *资产服务-人车绑定
 */
@Data
public class UserCarPageResDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime ;
	@ApiModelProperty("更新时间")
	private LocalDateTime modifyTime ;
	@ApiModelProperty("车牌号")
	private String pn ;
	@ApiModelProperty("司机姓名")
	private String userName ;
	@ApiModelProperty("司机电话")
	private String usrePhone ;

}

