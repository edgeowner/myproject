package com.huboot.business.base_model.pay.enums;

/**
* 会员支付平台
 */
public enum CustomerPaymentPlatformEnum {

  	OfflineTransfer(1, "线下转账", 1),
  	WechatPay(2, "微信支付", 2),
  	Alipay(3, "支付宝", 3),
  	ChinaUnionPay(4, "中国银联", 4),
  	SystemCalculation(5, "系统计算", 5),
	;

	//值
	private Integer value;
	//显示名称
	private String showName;
	//序列号
	private Integer seqNo;

	CustomerPaymentPlatformEnum(Integer value, String showName,Integer seqNo) {
		this.value = value;
		this.showName = showName;
		this.seqNo = seqNo;
	}

	public static CustomerPaymentPlatformEnum valueOf(int value) {
		for (CustomerPaymentPlatformEnum s : CustomerPaymentPlatformEnum.values()) {
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
