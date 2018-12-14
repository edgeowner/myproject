package com.huboot.business.base_model.pay.domain.account;


import com.huboot.business.mybatis.AbstractDomain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
*账户中心-子账户基础信息
*/
public class SubAccountBaseDomain  extends AbstractDomain<Long> implements Serializable {
    private static final long serialVersionUID = 1L;
    //账户id
    private Long accountId ;
    //子账户id
    private Long subAccountId ;
    //子账户余额
    private BigDecimal balance ;
    //可用余额--未启用
    private BigDecimal usableBalance ;
    //不可用余额(冻结)--未启用
    private BigDecimal unusableBalance ;
    //子账户类型,code=SUB_ACCOUNT_TYPE
    private Integer type ;
    //账户状态,code=ACCOUNT_STATUS
    private Integer status ;
    //所属账户
    private Long belongToAccountId ;
    //过期日期
    private Date expireDate ;
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
        
    public BigDecimal getBalance(){
        return this.balance;
    }

    public void setBalance(BigDecimal  balance){
        this.balance = balance;
    }
        
    public BigDecimal getUsableBalance(){
        return this.usableBalance;
    }

    public void setUsableBalance(BigDecimal  usableBalance){
        this.usableBalance = usableBalance;
    }
        
    public BigDecimal getUnusableBalance(){
        return this.unusableBalance;
    }

    public void setUnusableBalance(BigDecimal  unusableBalance){
        this.unusableBalance = unusableBalance;
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
        
    public Long getBelongToAccountId(){
        return this.belongToAccountId;
    }

    public void setBelongToAccountId(Long  belongToAccountId){
        this.belongToAccountId = belongToAccountId;
    }
        
    public Date getExpireDate(){
        return this.expireDate;
    }

    public void setExpireDate(Date  expireDate){
        this.expireDate = expireDate;
    }
        
    public Integer getVersion(){
        return this.version;
    }

    public void setVersion(Integer  version){
        this.version = version;
    }
                
}

