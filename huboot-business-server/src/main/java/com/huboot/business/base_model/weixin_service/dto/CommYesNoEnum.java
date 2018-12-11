package com.huboot.business.base_model.weixin_service.dto;

/**
* 全局是否值
 */
public enum CommYesNoEnum {
	None(0, "无", 0),
  	Yes(1, "是", 1),
  	No(2, "否", 2),
	;

	//值
	private Integer value;
	//显示名称
	private String showName;
	//序列号
	private Integer seqNo;

	CommYesNoEnum(Integer value, String showName, Integer seqNo) {
		this.value = value;
		this.showName = showName;
		this.seqNo = seqNo;
	}

	public static CommYesNoEnum valueOf(int value) {
		for (CommYesNoEnum s : CommYesNoEnum.values()) {
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
