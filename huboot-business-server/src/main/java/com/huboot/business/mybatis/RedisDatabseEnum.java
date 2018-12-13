package com.huboot.business.mybatis;

/**
 * 
* @ClassName: RedisDatabseEnum 
* @Description: redis缓存数据库定义
* @author zhangtiebin@hn-zhixin.com
* @date 2015年7月6日 下午8:41:13 
*
 */
public enum RedisDatabseEnum {
	
	SYS(0,"系统缓存"),
	SESSION(1,"SESSION缓存"),
	DICT(2,"字典缓存"),
    META(3,"元数据缓存-YOP等的基础数据缓存"),
    PAYMENT(4,"支付相关数据缓存，加速响应速度"),
    ;
    private final int databaseId;
    private String name;

    RedisDatabseEnum(int databaseId, String name) {
        this.databaseId = databaseId;
        this.name=name;
    }
	public int getDatabaseId() {
		return databaseId;
	} 
	public String getName() {
		return name;
	}

}

