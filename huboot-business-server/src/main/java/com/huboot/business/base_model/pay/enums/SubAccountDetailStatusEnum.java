package com.huboot.business.base_model.pay.enums;

/**
* 子账户明细状态
 */
public enum SubAccountDetailStatusEnum {

  	WaitForPay(1, "待付款", 1), //公用
  	Finished(2, "已完成", 2), //公用
  	Cancel(3, "已取消", 3), //公用
  	UnpayForAnother(4, "未代付", 4), //內管-代付
  	CommitedPayForAnother(5, "已提交代付", 5), //內管-代付
  	processing(7, "处理中", 7), //同调-提现
  	Received(8, "已到账", 8), //同调-提现
  	BackPayedForAnother(9, "代付被退回", 9), //內管-代付
  	CancelPayForAnother(10, "提现被撤销", 10), //內管-代付
  	CashFail(11, "提现失败", 11), //同调-提现
  	Paying(12, "付款中", 12), //第三方-付款中
  	PayedAnotherReceived(13, "已代付已到账", 13), //內管-代付
	Freeze(14, "已冻结", 14), //同调-提现
	WaitForRefund(15, "待退款", 15), //
	RefundSuccess(16, "退款完成", 16), //
	WaitFail(17, "退款失败", 17), //
	;

	//值
	private Integer value;
	//显示名称
	private String showName;
	//序列号
	private Integer seqNo;

	SubAccountDetailStatusEnum(Integer value, String showName, Integer seqNo) {
		this.value = value;
		this.showName = showName;
		this.seqNo = seqNo;
	}

	public static SubAccountDetailStatusEnum valueOf(int value) {
		for (SubAccountDetailStatusEnum s : SubAccountDetailStatusEnum.values()) {
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
