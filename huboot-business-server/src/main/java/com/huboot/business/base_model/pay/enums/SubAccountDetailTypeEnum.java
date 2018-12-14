package com.huboot.business.base_model.pay.enums;

/**
* 子账户明细类型
 */
public enum SubAccountDetailTypeEnum {

  	Recharge(1, "充值", 1), //
  	ChargeBack(2, "扣款", 2), //
  	Refund(3, "退款", 3), //
  	Adjust(4, "财务调整", 4), //
  	Cash(5, "提现", 5), //
  	TradeIncome(6, "订单收入", 6), ////同行订单收入
  	CashAudit(7, "提现审核", 7), //
  	CashFailBack(8, "提现失败返还", 8), //
  	TradeRewards(9, "订单奖励", 9), //
  	PayDeduction(10, "支付抵扣", 10), //
  	ExpireInvalid(11, "过期失效", 11), //
  	SystemProvide(12, "系统发放", 12), //
  	TradePay(13, "订单支付", 13), //
  	AddShopCar(14, "添加车辆", 14), //
  	DepositRefund(15, "押金转出", 15), //
  	RentRefund(16, "租车费用退款", 16), //
  	DepositOut(17, "押金转出", 17), //
  	DepositIn(18, "押金转入", 18), //
	ZKOrderPay(19, "直客租车订单支付", 19), //
	ZKOrderIncome(20, "直客租车订单收入", 20), //直客订单收入
	ZKRefundPay(21, "直客租车订单退款", 21), //直客订单退款
	ZKRefundIncome(22, "直客租车退款收入", 22), //直客退款收入
	MarketLoginIncome(23, "登录返现", 23), //营销-登录返现
	MarketTradeIncome(24, "交易抽奖返现", 24), //营销-交易抽奖返现
	ZKAddServiceRefundPay(25, "直客附加服务退款", 25), //直客附加服务退款
	ZKAddServiceRefundIncome(26, "直客附加服务退款收入", 26), //直客附加服务退款收入
	ZKShuttleOrderPay(27, "直客接送订单支付", 27), //
	ZKShuttleOrderIncome(28, "直客接送订单收入", 28),
	ZKShuttleRefundPay(29, "直客接送订单退款", 29),
	ZKShuttleRefundIncome(30, "直客接送退款收入", 30),
	ZKMallOrderPay(31, "直客商城订单支付", 31), //
	ZKMallOrderIncome(32, "直客商城订单收入", 32),
	ZKMallRefundPay(33, "直客商城订单退款", 33),
	ZKMallRefundIncome(34, "直客商城退款收入", 34),
	MiniRiskIncome(35, "风控小程序订单收入", 35),
	;

	//值
	private Integer value;
	//显示名称
	private String showName;
	//序列号
	private Integer seqNo;

	SubAccountDetailTypeEnum(Integer value, String showName, Integer seqNo) {
		this.value = value;
		this.showName = showName;
		this.seqNo = seqNo;
	}

	public static SubAccountDetailTypeEnum valueOf(int value) {
		for (SubAccountDetailTypeEnum s : SubAccountDetailTypeEnum.values()) {
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
