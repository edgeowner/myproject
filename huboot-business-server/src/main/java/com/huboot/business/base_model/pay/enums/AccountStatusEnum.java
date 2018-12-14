package com.huboot.business.base_model.pay.enums;

/**
* 账户状态
 */
public enum AccountStatusEnum {

  	Invalid(1, "无效", 1),
  	Valid(2, "有效", 2),
	;

	//值
	private Integer value;
	//显示名称
	private String showName;
	//序列号
	private Integer seqNo;

	AccountStatusEnum(Integer value, String showName,Integer seqNo) {
		this.value = value;
		this.showName = showName;
		this.seqNo = seqNo;
	}

	public static AccountStatusEnum valueOf(int value) {
		for (AccountStatusEnum s : AccountStatusEnum.values()) {
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
