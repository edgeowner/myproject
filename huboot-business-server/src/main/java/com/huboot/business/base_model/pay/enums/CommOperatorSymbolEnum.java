package com.huboot.business.base_model.pay.enums;

/**
* 公共运算符号
 */
public enum CommOperatorSymbolEnum {

  	Add(1, "+", 1),
  	Subtract(2, "-", 2),
  	Multiply(3, "×", 3),
  	Divide(4, "÷", 4),
	;

	//值
	private Integer value;
	//显示名称
	private String showName;
	//序列号
	private Integer seqNo;

	CommOperatorSymbolEnum(Integer value, String showName, Integer seqNo) {
		this.value = value;
		this.showName = showName;
		this.seqNo = seqNo;
	}

	public static CommOperatorSymbolEnum valueOf(int value) {
		for (CommOperatorSymbolEnum s : CommOperatorSymbolEnum.values()) {
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
