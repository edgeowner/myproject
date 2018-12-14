package com.huboot.business.base_model.pay.enums;

/**
* 报表中心账款类型
 */
public enum AccountFundTypeEnum {

  	None(0, "无", 0), //
  	OrderPrePay(1, "订单预付款", 1), //
  	OrderContinue(2, "订单续租", 2), //
  	OrderSettled(3, "订单结算", 3), //
  	OrderRefund(4, "订单退款", 4), //
  	MonthlyRentBill(5, "月租账单", 5), //
	;

	//值
	private Integer value;
	//显示名称
	private String showName;
	//序列号
	private Integer seqNo;

	AccountFundTypeEnum(Integer value, String showName, Integer seqNo) {
		this.value = value;
		this.showName = showName;
		this.seqNo = seqNo;
	}

	public static AccountFundTypeEnum valueOf(int value) {
		for (AccountFundTypeEnum s : AccountFundTypeEnum.values()) {
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
