package com.huboot.business.base_model.pay.dto.account;


import com.huboot.business.base_model.pay.domain.account.SubAccountBaseDomain;

import java.io.Serializable;

//账户中心-子账户
public class SubAccountDTO  implements Serializable {
	private static final long serialVersionUID = 1L;
	//唯一标识
	private SubAccountBaseDomain subAccountBaseDomain;

	public SubAccountBaseDomain getSubAccountBaseDomain() {
		return subAccountBaseDomain;
	}

	public void setSubAccountBaseDomain(SubAccountBaseDomain subAccountBaseDomain) {
		this.subAccountBaseDomain = subAccountBaseDomain;
	}
}

