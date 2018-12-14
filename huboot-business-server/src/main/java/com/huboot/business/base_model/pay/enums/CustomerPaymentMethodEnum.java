package com.huboot.business.base_model.pay.enums;

/**
* 会员支付方式
 */
public enum CustomerPaymentMethodEnum {

  	None(0, "无", 0),
  	OnLine(1, "线上支付", 1),
  	Offline(2, "线下支付", 2),
	;

	//值
	private Integer value;
	//显示名称
	private String showName;
	//序列号
	private Integer seqNo;

	CustomerPaymentMethodEnum(Integer value, String showName,Integer seqNo) {
		this.value = value;
		this.showName = showName;
		this.seqNo = seqNo;
	}

	public static CustomerPaymentMethodEnum valueOf(int value) {
		for (CustomerPaymentMethodEnum s : CustomerPaymentMethodEnum.values()) {
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
