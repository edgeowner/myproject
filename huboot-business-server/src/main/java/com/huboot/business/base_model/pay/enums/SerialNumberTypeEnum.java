package com.huboot.business.base_model.pay.enums;

/**
* 单据序列号类型
 */
public enum SerialNumberTypeEnum {

  	InquirySheet(1, "", 1),
  	OfferSheet(2, "", 2),
  	Order(3, "订单编号", 3),
  	Payment(4, "支付编号", 4),
  	Shop(5, "店铺编号", 5),
  	Company(6, "公司", 6),
  	RiskOrder(7, "风控订单编号", 7),
  	AccountDetail(8, "子账户明细编号", 8),
  	TradeOrder(9, "交单编号", 9),
	DirectClientOrder(10, "订单编号", 10),
	MiniRiskOrder(11, "", 11),
	;

	//值
	private Integer value;
	//显示名称
	private String showName;
	//序列号
	private Integer seqNo;

	SerialNumberTypeEnum(Integer value, String showName, Integer seqNo) {
		this.value = value;
		this.showName = showName;
		this.seqNo = seqNo;
	}

	public static SerialNumberTypeEnum valueOf(int value) {
		for (SerialNumberTypeEnum s : SerialNumberTypeEnum.values()) {
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
