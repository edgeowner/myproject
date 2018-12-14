package com.huboot.business.base_model.pay.enums;

/**
* 会员支付状态
 */
public enum CustomerPaymentStatusEnum {

  	None(0, "无", 0), //
  	Unpaid(1, "未支付", 1), //
  	Paying(2, "支付中", 2), //
  	Paid(3, "已支付", 3), //
  	PayFail(4, "支付失败", 4), //
  	PayClose(5, "支付关闭", 5), //
  	NotNeedPay(6, "无需支付", 6), //用于账单未出账
  	NoBillPayClose(7, "未出账支付关闭", 7), //用于未出账单关闭
	;

	//值
	private Integer value;
	//显示名称
	private String showName;
	//序列号
	private Integer seqNo;

	CustomerPaymentStatusEnum(Integer value, String showName, Integer seqNo) {
		this.value = value;
		this.showName = showName;
		this.seqNo = seqNo;
	}

	public static CustomerPaymentStatusEnum valueOf(int value) {
		for (CustomerPaymentStatusEnum s : CustomerPaymentStatusEnum.values()) {
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
