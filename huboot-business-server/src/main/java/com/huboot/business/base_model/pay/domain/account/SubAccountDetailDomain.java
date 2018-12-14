package com.huboot.business.base_model.pay.domain.account;


import com.huboot.business.mybatis.AbstractDomain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
*账户中心-子账户明细
*/
public class SubAccountDetailDomain  extends AbstractDomain<Long> implements Serializable {
    private static final long serialVersionUID = 1L;
    //主账户id
    private Long accountId ;
    //子账户id
    private Long subAccountId ;
    //明细编号
    private String sn ;
    //交易系统-订单编号
    private String tradeSn ;
    //交易来源,code=SUB_ACCOUNT_DETAIL_TRADE_SOURCE
    private Integer tradeSource ;
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
    //子账户明细类型,code=SUB_ACCOUNT_DETAIL_TYPE
    private Integer type ;
    //子账户明细状态,code=SUB_ACCOUNT_DETAIL_STATUS
    private Integer status ;
    //备注信息
    private String remark ;
    //操作人
    private String operator ;
    //操作人类型,code=SUB_ACCOUNT_DETAIL_OPERATOR_TYPE
    private Integer operatorType ;
    //操作人手机号
    private String operatorPhone ;
    //关联账单id
    private Long relaDetailId ;
    //更新版本号
    private Integer version ;
                
    public Long getAccountId(){
        return this.accountId;
    }

    public void setAccountId(Long  accountId){
        this.accountId = accountId;
    }
        
    public Long getSubAccountId(){
        return this.subAccountId;
    }

    public void setSubAccountId(Long  subAccountId){
        this.subAccountId = subAccountId;
    }
        
    public String getSn(){
        return this.sn;
    }

    public void setSn(String  sn){
        this.sn = sn;
    }
        
    public String getTradeSn(){
        return this.tradeSn;
    }

    public void setTradeSn(String  tradeSn){
        this.tradeSn = tradeSn;
    }
        
    public Integer getTradeSource(){
        return this.tradeSource;
    }

    public void setTradeSource(Integer  tradeSource){
        this.tradeSource = tradeSource;
    }
        
    public Integer getSign(){
        return this.sign;
    }

    public void setSign(Integer  sign){
        this.sign = sign;
    }
        
    public BigDecimal getOriginalAmount(){
        return this.originalAmount;
    }

    public void setOriginalAmount(BigDecimal  originalAmount){
        this.originalAmount = originalAmount;
    }
        
    public BigDecimal getAmountPaid(){
        return this.amountPaid;
    }

    public void setAmountPaid(BigDecimal  amountPaid){
        this.amountPaid = amountPaid;
    }
        
    public BigDecimal getChangedAmount(){
        return this.changedAmount;
    }

    public void setChangedAmount(BigDecimal  changedAmount){
        this.changedAmount = changedAmount;
    }
        
    public Date getPaymentDate(){
        return this.paymentDate;
    }

    public void setPaymentDate(Date  paymentDate){
        this.paymentDate = paymentDate;
    }
        
    public Integer getType(){
        return this.type;
    }

    public void setType(Integer  type){
        this.type = type;
    }
        
    public Integer getStatus(){
        return this.status;
    }

    public void setStatus(Integer  status){
        this.status = status;
    }
        
    public String getRemark(){
        return this.remark;
    }

    public void setRemark(String  remark){
        this.remark = remark;
    }
        
    public String getOperator(){
        return this.operator;
    }

    public void setOperator(String  operator){
        this.operator = operator;
    }
        
    public Integer getOperatorType(){
        return this.operatorType;
    }

    public void setOperatorType(Integer  operatorType){
        this.operatorType = operatorType;
    }
        
    public String getOperatorPhone(){
        return this.operatorPhone;
    }

    public void setOperatorPhone(String  operatorPhone){
        this.operatorPhone = operatorPhone;
    }
        
    public Long getRelaDetailId(){
        return this.relaDetailId;
    }

    public void setRelaDetailId(Long  relaDetailId){
        this.relaDetailId = relaDetailId;
    }
        
    public Integer getVersion(){
        return this.version;
    }

    public void setVersion(Integer  version){
        this.version = version;
    }
                
}

