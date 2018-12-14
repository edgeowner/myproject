package com.huboot.business.base_model.pay.dto.account;


import com.huboot.business.base_model.pay.domain.account.SubAccountDetailDomain;

import java.io.Serializable;

//账户中心-子账户明细
public class SubAccountDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private SubAccountDetailDomain subAccountDetailDomain;

    private SubAccountDetailExtendDTO subAccountDetailExtendDTO;

    public SubAccountDetailExtendDTO getSubAccountDetailExtendDTO() {
        return subAccountDetailExtendDTO;
    }

    public void setSubAccountDetailExtendDTO(SubAccountDetailExtendDTO subAccountDetailExtendDTO) {
        this.subAccountDetailExtendDTO = subAccountDetailExtendDTO;
    }

    public SubAccountDetailDomain getSubAccountDetailDomain() {
        return subAccountDetailDomain;
    }

    public void setSubAccountDetailDomain(SubAccountDetailDomain subAccountDetailDomain) {
        this.subAccountDetailDomain = subAccountDetailDomain;
    }
}

