package com.huboot.business.base_model.pay.enums;

/**
* 会员支付PINGPP操作类型
 */
public enum CustomerPaymentPingppOperateTypeEnum {

  	None(0, "无", 0),
  	POST(1, "创建", 1),
  	GET(2, "查询", 2),
  	PATCH(3, "修改", 3),
  	WEBHOOKS(4, "异步通知", 4),
	;

	//值
	private Integer value;
	//显示名称
	private String showName;
	//序列号
	private Integer seqNo;

	CustomerPaymentPingppOperateTypeEnum(Integer value, String showName,Integer seqNo) {
		this.value = value;
		this.showName = showName;
		this.seqNo = seqNo;
	}

	public static CustomerPaymentPingppOperateTypeEnum valueOf(int value) {
		for (CustomerPaymentPingppOperateTypeEnum s : CustomerPaymentPingppOperateTypeEnum.values()) {
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
