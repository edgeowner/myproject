package com.huboot.account.account.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.SqlResultSetMapping;
import java.io.Serializable;

/**
 *子账户
 */
@Data
public class SubAccountBillDetailPagerDTO implements Serializable {

	@ApiModelProperty("流水号")
	private String tripartiteSeq ;
	@ApiModelProperty("类型")
	private String orderSource ;
	@ApiModelProperty("类型")
	private String orderSourceName ;
	@ApiModelProperty("账户原金额")
	private String preAmount ;
	@ApiModelProperty("变动金额")
	private String amount ;
	@ApiModelProperty("变动后金额")
	private String afterAmount ;
	@ApiModelProperty("创建人")
	private String creatorId ;
	@ApiModelProperty("创建人")
	private String creatorName ;
	@ApiModelProperty("创建时间")
	private String createTime ;
	@ApiModelProperty("备注")
	private String remark ;

}

