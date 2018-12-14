package com.huboot.business.base_model.pay.dto.account;

import java.io.Serializable;

//账户中心-账户
public class AccountAddDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    //店铺id
    private Long shopId ;
    //店铺名称
    private String shopName ;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}

