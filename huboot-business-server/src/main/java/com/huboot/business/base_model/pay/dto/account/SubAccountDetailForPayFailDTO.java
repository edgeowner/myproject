package com.huboot.business.base_model.pay.dto.account;

import java.io.Serializable;

//账户中心-子账户明细
public class SubAccountDetailForPayFailDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    //唯一标识
    private Long detailId;
    //支付成功日期
    private String remark;

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

