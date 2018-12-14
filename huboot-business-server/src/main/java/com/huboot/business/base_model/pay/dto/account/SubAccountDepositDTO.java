package com.huboot.business.base_model.pay.dto.account;

import java.io.Serializable;

//账户中心-押金账户扩展
public class SubAccountDepositDTO  implements Serializable {
	private static final long serialVersionUID = 1L;
	//唯一标识
	private Long id ;
	//子账户id
	private Long subAccountId ;
	//卖家店铺id
	private Long sellerShopId ;
	//卖家店铺名称
	private String sellerShopName ;
 
 	public void setId(Long  id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}
   
 	public void setSubAccountId(Long  subAccountId){
		this.subAccountId = subAccountId;
	}

	public Long getSubAccountId(){
		return this.subAccountId;
	}
 
 	public void setSellerShopId(Long  sellerShopId){
		this.sellerShopId = sellerShopId;
	}

	public Long getSellerShopId(){
		return this.sellerShopId;
	}
 
 	public void setSellerShopName(String  sellerShopName){
		this.sellerShopName = sellerShopName;
	}

	public String getSellerShopName(){
		return this.sellerShopName;
	}
   
}

