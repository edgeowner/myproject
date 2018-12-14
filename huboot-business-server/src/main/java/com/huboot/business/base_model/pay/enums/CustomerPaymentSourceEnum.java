package com.huboot.business.base_model.pay.enums;

/**
* 会员支付来源
 */
public enum CustomerPaymentSourceEnum {

  	OrderPay(1, "订单支付", 1),
  	OrderAgentPay(2, "订单代付", 2),
  	RiskOrderPay(3, "风控订单支付", 3),
	;

	//值
	private Integer value;
	//显示名称
	private String showName;
	//序列号
	private Integer seqNo;

	CustomerPaymentSourceEnum(Integer value, String showName,Integer seqNo) {
		this.value = value;
		this.showName = showName;
		this.seqNo = seqNo;
	}

	public static CustomerPaymentSourceEnum valueOf(int value) {
		for (CustomerPaymentSourceEnum s : CustomerPaymentSourceEnum.values()) {
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
