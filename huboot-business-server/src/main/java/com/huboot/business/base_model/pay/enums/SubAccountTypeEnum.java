package com.huboot.business.base_model.pay.enums;

/**
* 子账户类型
 */
public enum SubAccountTypeEnum {

  	DepositAccount(1, "押金账户", 1),
  	BalanceAccount(2, "余额账户", 2),
  	RedPacketAccount(3, "红包账户", 3),
  	ThirdPartyAccount(4, "第三方账户", 4),
  	BillAccount(5, "账单账户", 5),
	;

	//值
	private Integer value;
	//显示名称
	private String showName;
	//序列号
	private Integer seqNo;

	SubAccountTypeEnum(Integer value, String showName,Integer seqNo) {
		this.value = value;
		this.showName = showName;
		this.seqNo = seqNo;
	}

	public static SubAccountTypeEnum valueOf(int value) {
		for (SubAccountTypeEnum s : SubAccountTypeEnum.values()) {
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
