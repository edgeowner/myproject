package com.huboot.business.base_model.pay.dto.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//账户中心-子账户明细
public class SubAccountCashBackFailDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	//唯一标识
	private Long id ;
	//主账户id
	private Long accountId ;
	//子账户id
	private Long subAccountId ;
	//明细编号
	private String sn ;
	//运算符号,code=COMM_OPERATOR_SYMBOL
	private Integer sign ;
	//原金额
	private BigDecimal originalAmount ;
	//交易金额
	private BigDecimal amountPaid ;
	//变更后金额
	private BigDecimal changedAmount ;
	//支付成功日期
	private Date paymentDate ;
	//子账户明细类型,enum=subAccountDetailType
	private Integer type ;
	//子账户明细状态,enum=subAccountDetailStatus
	private Integer status ;
	//备注信息
	private String remark ;
	//交易系统-订单编号
	private String tradeSn ;
	//操作人
	private String operator ;
 
 	public void setId(Long  id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}
   
 	public void setAccountId(Long  accountId){
		this.accountId = accountId;
	}

	public Long getAccountId(){
		return this.accountId;
	}
 
 	public void setSubAccountId(Long  subAccountId){
		this.subAccountId = subAccountId;
	}

	public Long getSubAccountId(){
		return this.subAccountId;
	}
 
 	public void setSn(String  sn){
		this.sn = sn;
	}

	public String getSn(){
		return this.sn;
	}

	public Integer getSign() {
		return sign;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}

	public void setOriginalAmount(BigDecimal  originalAmount){
		this.originalAmount = originalAmount;
	}

	public BigDecimal getOriginalAmount(){
		return this.originalAmount;
	}
 
 	public void setAmountPaid(BigDecimal  amountPaid){
		this.amountPaid = amountPaid;
	}

	public BigDecimal getAmountPaid(){
		return this.amountPaid;
	}
 
 	public void setChangedAmount(BigDecimal  changedAmount){
		this.changedAmount = changedAmount;
	}

	public BigDecimal getChangedAmount(){
		return this.changedAmount;
	}
 
 	public void setPaymentDate(Date  paymentDate){
		this.paymentDate = paymentDate;
	}

	public Date getPaymentDate(){
		return this.paymentDate;
	}
 
 	public void setType(Integer  type){
		this.type = type;
	}

	public Integer getType(){
		return this.type;
	}
 
 	public void setStatus(Integer  status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}
 
 	public void setRemark(String  remark){
		this.remark = remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public String getTradeSn() {
		return tradeSn;
	}

	public void setTradeSn(String tradeSn) {
		this.tradeSn = tradeSn;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}

