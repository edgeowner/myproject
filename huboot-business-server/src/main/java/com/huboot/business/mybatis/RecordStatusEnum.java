package com.huboot.business.mybatis;

/***
 * 
* @ClassName: RecordStatusEnum 
* @Description: 系统数据记录状态枚举
* @author zhangtiebin@hn-zhixin.com
* @date 2015年6月24日 上午10:41:00 
*
 */
public enum RecordStatusEnum {
	deleted(0,"删除"),
	activited(1,"启用"),
	disable(2,"禁用"), 
	complete(3,"完成");

    private int value;
    private String name; 
    RecordStatusEnum(int value, String name) {
    	this.value = value;
    	this.name = name;
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
  
}
