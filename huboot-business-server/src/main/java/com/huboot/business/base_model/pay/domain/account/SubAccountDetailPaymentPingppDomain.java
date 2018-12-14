package com.huboot.business.base_model.pay.domain.account;


import com.huboot.business.mybatis.AbstractDomain;

import java.io.Serializable;

/**
*账户中心-子账户明细支付PINGPP
*/
public class SubAccountDetailPaymentPingppDomain  extends AbstractDomain<Long> implements Serializable {
    private static final long serialVersionUID = 1L;
    //子账户明细支付ID
    private Long subAccountDetailPaymentId ;
    //子账户明细支付编号
    private String subAccountDetailPaymentSn ;
    //子账户明细支付编号
    private String pingppId ;
    //发送的请求体
    private String pingppRequest ;
    //返回的响应体
    private String pingppResponse ;
    //操作类型,code=SUB_ACCOUNT_DETAIL_PAYMENT__PINGPP_OPERATE_TYPE
    private Integer operateType ;
    //更新版本号
    private Integer version ;
                
    public Long getSubAccountDetailPaymentId(){
        return this.subAccountDetailPaymentId;
    }

    public void setSubAccountDetailPaymentId(Long  subAccountDetailPaymentId){
        this.subAccountDetailPaymentId = subAccountDetailPaymentId;
    }
        
    public String getSubAccountDetailPaymentSn(){
        return this.subAccountDetailPaymentSn;
    }

    public void setSubAccountDetailPaymentSn(String  subAccountDetailPaymentSn){
        this.subAccountDetailPaymentSn = subAccountDetailPaymentSn;
    }
        
    public String getPingppId(){
        return this.pingppId;
    }

    public void setPingppId(String  pingppId){
        this.pingppId = pingppId;
    }
        
    public String getPingppRequest(){
        return this.pingppRequest;
    }

    public void setPingppRequest(String  pingppRequest){
        this.pingppRequest = pingppRequest;
    }
        
    public String getPingppResponse(){
        return this.pingppResponse;
    }

    public void setPingppResponse(String  pingppResponse){
        this.pingppResponse = pingppResponse;
    }
        
    public Integer getOperateType(){
        return this.operateType;
    }

    public void setOperateType(Integer  operateType){
        this.operateType = operateType;
    }
        
    public Integer getVersion(){
        return this.version;
    }

    public void setVersion(Integer  version){
        this.version = version;
    }
                
}

