package com.huboot.business.base_model.pay.enums;

/**
* 子账户明细支付类型
 */
public enum SubAccountDetailPaymentTypeEnum {

	none(0, "", 0),
  	OfflineTransfer(1, "线下转账", 1),
  	JSAPI(2, "微信公众号支付", 2),
  	NATIVE(3, "微信原生扫码支付", 3),
  	APP(4, "微信app支付", 4),
  	UnionAgentPay(5, "银联代付", 5),
  	OfflineSellerInstead(6, "线下支付卖家代操作", 6),
  	DepositOffset(7, "押金冲抵", 7),
  	FinanceCheckOff(8, "财务核销", 8),
  	AlipayWap(9, "支付宝手机网页支付", 9),
  	RedPacket(10, "红包抵扣", 10),
  	ApplyForPaid(11, "申请代付", 11),
	wx_lite(12, "微信小程序支付", 12),
	;

	//值
	private Integer value;
	//显示名称
	private String showName;
	//序列号
	private Integer seqNo;

	SubAccountDetailPaymentTypeEnum(Integer value, String showName,Integer seqNo) {
		this.value = value;
		this.showName = showName;
		this.seqNo = seqNo;
	}

	public static SubAccountDetailPaymentTypeEnum valueOf(int value) {
		for (SubAccountDetailPaymentTypeEnum s : SubAccountDetailPaymentTypeEnum.values()) {
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
