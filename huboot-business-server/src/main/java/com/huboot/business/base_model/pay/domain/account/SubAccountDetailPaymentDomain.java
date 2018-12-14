package com.huboot.business.base_model.pay.domain.account;


import com.huboot.business.mybatis.AbstractDomain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
*账户中心-子账户明细支付
*/
public class SubAccountDetailPaymentDomain  extends AbstractDomain<Long> implements Serializable {
    private static final long serialVersionUID = 1L;
    //子账户id
    private Long subAccountId ;
    //子账户id
    private Long subAccountDetailId ;
    //支付编号
    private String sn ;
    //子账户明细支付类型,code=SUB_ACCOUNT_DETAIL_PAYMENT_TYPE
    private Integer type ;
    //子账户明细支付平台,code=SUB_ACCOUNT_DETAIL_PAYMENT_PLATFORM
    private Integer platform ;
    //子账户明细支付方式,code=SUB_ACCOUNT_DETAIL_PAYMENT_METHOD
    private Integer method ;
    //交易流水号（第三方）
    private String transactionNo ;
    //支付成功日期
    private Date paymentDate ;
    //金额
    private BigDecimal amount ;
    //备注信息
    private String remark ;
    //子账户明细支付状态,code=SUB_ACCOUNT_DETAIL_PAYMENT_STATUS
    private Integer status ;
    //是否代付：1，是；2.否
    private Integer isSharePay ;
    //更新版本号
    private Integer version ;
                
    public Long getSubAccountId(){
        return this.subAccountId;
    }

    public void setSubAccountId(Long  subAccountId){
        this.subAccountId = subAccountId;
    }
        
    public Long getSubAccountDetailId(){
        return this.subAccountDetailId;
    }

    public void setSubAccountDetailId(Long  subAccountDetailId){
        this.subAccountDetailId = subAccountDetailId;
    }
        
    public String getSn(){
        return this.sn;
    }

    public void setSn(String  sn){
        this.sn = sn;
    }
        
    public Integer getType(){
        return this.type;
    }

    public void setType(Integer  type){
        this.type = type;
    }
        
    public Integer getPlatform(){
        return this.platform;
    }

    public void setPlatform(Integer  platform){
        this.platform = platform;
    }
        
    public Integer getMethod(){
        return this.method;
    }

    public void setMethod(Integer  method){
        this.method = method;
    }
        
    public String getTransactionNo(){
        return this.transactionNo;
    }

    public void setTransactionNo(String  transactionNo){
        this.transactionNo = transactionNo;
    }
        
    public Date getPaymentDate(){
        return this.paymentDate;
    }

    public void setPaymentDate(Date  paymentDate){
        this.paymentDate = paymentDate;
    }
        
    public BigDecimal getAmount(){
        return this.amount;
    }

    public void setAmount(BigDecimal  amount){
        this.amount = amount;
    }
        
    public String getRemark(){
        return this.remark;
    }

    public void setRemark(String  remark){
        this.remark = remark;
    }
        
    public Integer getStatus(){
        return this.status;
    }

    public void setStatus(Integer  status){
        this.status = status;
    }
        
    public Integer getIsSharePay(){
        return this.isSharePay;
    }

    public void setIsSharePay(Integer  isSharePay){
        this.isSharePay = isSharePay;
    }
        
    public Integer getVersion(){
        return this.version;
    }

    public void setVersion(Integer  version){
        this.version = version;
    }
                
}

