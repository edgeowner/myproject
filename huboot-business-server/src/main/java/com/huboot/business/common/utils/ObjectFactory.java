package com.huboot.business.common.utils;

/**
 * 
 * @author zhangtiebin@bwcmall.com
 * @description  数组拷贝时对象创建工具
 * @class ObjectFactory
 * @package com.bwcmall.core.utils
 * @Date 2016年5月25日 下午6:07:16
 * @param <T>
 */
public abstract class ObjectFactory<S,T> {

	public void before(S source, T target) {
		
	}

	public abstract T newInstance();

	public void after(S source, T target) {

	}
}