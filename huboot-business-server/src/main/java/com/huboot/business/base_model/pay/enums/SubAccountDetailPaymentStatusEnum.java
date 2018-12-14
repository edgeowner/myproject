package com.huboot.business.base_model.pay.enums;

/**
* 子账户明细支付状态
 */
public enum SubAccountDetailPaymentStatusEnum {

  	Unpaid(1, "未支付", 1),
  	Paying(2, "支付中", 2),
  	Paid(3, "已支付", 3),
  	PayFail(4, "支付失败", 4),
  	PayClose(5, "支付关闭", 5),
	;

	//值
	private Integer value;
	//显示名称
	private String showName;
	//序列号
	private Integer seqNo;

	SubAccountDetailPaymentStatusEnum(Integer value, String showName,Integer seqNo) {
		this.value = value;
		this.showName = showName;
		this.seqNo = seqNo;
	}

	public static SubAccountDetailPaymentStatusEnum valueOf(int value) {
		for (SubAccountDetailPaymentStatusEnum s : SubAccountDetailPaymentStatusEnum.values()) {
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
