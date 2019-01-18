package com.huboot.user.user.dto;

import java.io.Serializable;

import com.huboot.commons.page.AbstractQueryReqDTO;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *资产服务-人车绑定
 */
@Data
public class UserCarQueryReqDTO extends AbstractQueryReqDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty("车牌号")
	private String pn;
	@ApiModelProperty("司机姓名")
	private String driverName ;
	@ApiModelProperty("司机手机")
	private String driverPhone ;

}

