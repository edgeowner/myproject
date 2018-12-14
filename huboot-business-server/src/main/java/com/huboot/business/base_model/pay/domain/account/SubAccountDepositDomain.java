package com.huboot.business.base_model.pay.domain.account;


import com.huboot.business.mybatis.AbstractDomain;

import java.io.Serializable;

/**
*账户中心-押金账户扩展
*/
public class SubAccountDepositDomain  extends AbstractDomain<Long> implements Serializable {
    private static final long serialVersionUID = 1L;
    //子账户id
    private Long subAccountId ;
    //卖家店铺ID
    private Long sellerShopId ;
    //卖家店铺名称
    private String sellerShopName ;
                
    public Long getSubAccountId(){
        return this.subAccountId;
    }

    public void setSubAccountId(Long  subAccountId){
        this.subAccountId = subAccountId;
    }
        
    public Long getSellerShopId(){
        return this.sellerShopId;
    }

    public void setSellerShopId(Long  sellerShopId){
        this.sellerShopId = sellerShopId;
    }
        
    public String getSellerShopName(){
        return this.sellerShopName;
    }

    public void setSellerShopName(String  sellerShopName){
        this.sellerShopName = sellerShopName;
    }
                
}

