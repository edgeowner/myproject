package com.huboot.business.base_model.pay.enums;

/**
* 会员支付他人代付状态
 */
public enum CustomerPaymentOtherPayStatusEnum {

  	valid(1, "有效", 1),
  	invalid(2, "无效", 2),
	;

	//值
	private Integer value;
	//显示名称
	private String showName;
	//序列号
	private Integer seqNo;

	CustomerPaymentOtherPayStatusEnum(Integer value, String showName,Integer seqNo) {
		this.value = value;
		this.showName = showName;
		this.seqNo = seqNo;
	}

	public static CustomerPaymentOtherPayStatusEnum valueOf(int value) {
		for (CustomerPaymentOtherPayStatusEnum s : CustomerPaymentOtherPayStatusEnum.values()) {
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
