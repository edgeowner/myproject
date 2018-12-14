package com.huboot.business.base_model.pay.enums;

/**
* 车辆事件来源
 */
public enum SubAccountDetailTradeSourceEnum {

  	TradeOrder(1, "交易系统-订单", 1),
  	AccountDetail(2, "账户系统-明细", 2),
  	ProductShopCar(3, "商品系统-车辆", 3),
	TradeRefund(4, "交易系统-退款", 4),
	;

	//值
	private Integer value;
	//显示名称
	private String showName;
	//序列号
	private Integer seqNo;

	SubAccountDetailTradeSourceEnum(Integer value, String showName,Integer seqNo) {
		this.value = value;
		this.showName = showName;
		this.seqNo = seqNo;
	}

	public static SubAccountDetailTradeSourceEnum valueOf(int value) {
		for (SubAccountDetailTradeSourceEnum s : SubAccountDetailTradeSourceEnum.values()) {
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
