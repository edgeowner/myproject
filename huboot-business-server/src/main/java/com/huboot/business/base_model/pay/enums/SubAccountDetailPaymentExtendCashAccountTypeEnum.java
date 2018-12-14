package com.huboot.business.base_model.pay.enums;

/**
* 子账户明细支付扩展提现账户类型
 */
public enum SubAccountDetailPaymentExtendCashAccountTypeEnum {

  	PersonalAccount(1, "个人账户", 1),
  	CompanyAccount(2, "对公帐户", 2),
	;

	//值
	private Integer value;
	//显示名称
	private String showName;
	//序列号
	private Integer seqNo;

	SubAccountDetailPaymentExtendCashAccountTypeEnum(Integer value, String showName,Integer seqNo) {
		this.value = value;
		this.showName = showName;
		this.seqNo = seqNo;
	}

	public static SubAccountDetailPaymentExtendCashAccountTypeEnum valueOf(int value) {
		for (SubAccountDetailPaymentExtendCashAccountTypeEnum s : SubAccountDetailPaymentExtendCashAccountTypeEnum.values()) {
			if (s.value.equals(value))
				return s;
		}
		throw new IllegalArgumentException(String.format("值%s不能转换成枚举", value));
	}

	public String getShowName() {
		return showName;
	}

	public Integer getValue() {
		return value;
	}

	public Integer getSeqNo() {
		return seqNo;
	}
}
