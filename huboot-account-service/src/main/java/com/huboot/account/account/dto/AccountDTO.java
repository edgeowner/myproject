package com.huboot.account.account.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *主账户
 */
@Data
public class AccountDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("账户所属（个人or店铺）")
	private String type ;
	@ApiModelProperty("关联id")
	private String relaId ;
	@ApiModelProperty("状态")
	private String status ;

}

