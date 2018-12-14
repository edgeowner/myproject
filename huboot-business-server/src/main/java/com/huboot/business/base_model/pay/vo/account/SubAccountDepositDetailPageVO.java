package com.huboot.business.base_model.pay.vo.account;

import java.io.Serializable;
import java.util.List;

public class SubAccountDepositDetailPageVO implements Serializable {
	private static final long serialVersionUID = 1L;
	// 创建时间
	private String createMonth;
	private List<SubAccountDepositDetailListVO> detailList;

	public String getCreateMonth() {
		return createMonth;
	}

	public void setCreateMonth(String createMonth) {
		this.createMonth = createMonth;
	}

	public List<SubAccountDepositDetailListVO> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<SubAccountDepositDetailListVO> detailList) {
		this.detailList = detailList;
	}
}

