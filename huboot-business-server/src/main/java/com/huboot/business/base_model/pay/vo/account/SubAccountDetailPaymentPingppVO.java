package com.huboot.business.base_model.pay.vo.account;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

//@ApiModel(value = "账户中心-子账户明细支付PINGPP", description = "账户中心-子账户明细支付PINGPP")
public class SubAccountDetailPaymentPingppVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("支付ID")
	private Long paymentId ;
	@ApiModelProperty("发送的请求体")
	private String pingppRequest ;
	@ApiModelProperty("返回的响应体")
	private String pingppResponse ;
	@ApiModelProperty("操作类型,code=SUB_ACCOUNT_DETAIL_PAYMENT__PINGPP_OPERATE_TYPE")
	private Integer operateType ;

	public void setId(Long id){
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}
	
	public void setPaymentId(Long paymentId){
		this.paymentId = paymentId;
	}

	public Long getPaymentId() {
		return this.paymentId;
	}
	
	public void setPingppRequest(String pingppRequest){
		this.pingppRequest = pingppRequest;
	}

	public String getPingppRequest() {
		return this.pingppRequest;
	}
	
	public void setPingppResponse(String pingppResponse){
		this.pingppResponse = pingppResponse;
	}

	public String getPingppResponse() {
		return this.pingppResponse;
	}
	
	public void setOperateType(Integer operateType){
		this.operateType = operateType;
	}

	public Integer getOperateType() {
		return this.operateType;
	}
	
}

