package com.huboot.business.base_model.pay.domain.account;


import com.huboot.business.mybatis.AbstractDomain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
*账户中心-账户基础信息表
*/
public class AccountBaseDomain  extends AbstractDomain<Long> implements Serializable {
    private static final long serialVersionUID = 1L;
    //账户id
    private Long accountId ;
    //店铺ID
    private Long shopId ;
    //店铺名称
    private String shopName ;
    //用户gid
    private String userGid ;
    //账户总资产
    private BigDecimal totalAssets ;
    //账户状态,code=ACCOUNT_STATUS
    private Integer status ;
    //更新版本号
    private Integer version ;
                
    public Long getAccountId(){
        return this.accountId;
    }

    public void setAccountId(Long  accountId){
        this.accountId = accountId;
    }
        
    public Long getShopId(){
        return this.shopId;
    }

    public void setShopId(Long  shopId){
        this.shopId = shopId;
    }
        
    public String getShopName(){
        return this.shopName;
    }

    public void setShopName(String  shopName){
        this.shopName = shopName;
    }
        
    public String getUserGid(){
        return this.userGid;
    }

    public void setUserGid(String  userGid){
        this.userGid = userGid;
    }
        
    public BigDecimal getTotalAssets(){
        return this.totalAssets;
    }

    public void setTotalAssets(BigDecimal  totalAssets){
        this.totalAssets = totalAssets;
    }
        
    public Integer getStatus(){
        return this.status;
    }

    public void setStatus(Integer  status){
        this.status = status;
    }
        
    public Integer getVersion(){
        return this.version;
    }

    public void setVersion(Integer  version){
        this.version = version;
    }
                
}

