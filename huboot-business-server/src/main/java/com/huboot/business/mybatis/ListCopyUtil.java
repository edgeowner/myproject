package com.huboot.business.mybatis;

import com.huboot.business.common.utils.ObjectFactory;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 数组拷贝工具
 * @author zhangtiebin@bwcmall.com
 * @description  
 * @class ListCopyUtil
 * @package com.bwcmall.core.utils
 * @Date 2016年5月25日 下午5:47:42
 */
public class ListCopyUtil {
	
	/**
	 * 拷贝工具
	 * @param <T>
	 * @param
	 * @param factory
	 * @return
	 */
	public static <S,T> List<T> copyAsList(Iterator<S> it,ObjectFactory<S,T> factory){
		List<T> targetList = new ArrayList<>();
		if(it != null ){
			T target = null;
			S source = null;
			while(it.hasNext()){
				source = it.next();
				target = factory.newInstance();
				factory.before(source, target);
				deepCopy(source,target);
				factory.after(source, target);
				targetList.add(target);
			}
		}
		return targetList;
	}
	/**
	 * 拷贝工具
	 * @param
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> copyAsList(Iterator<?> it,Class<T> clazz){
		List<T> targetList = new ArrayList<>();
		if(it != null ){
			T target = null;
			try {
				while(it.hasNext()){
					target = clazz.newInstance();
					deepCopy(it.next(),target);
					targetList.add(target);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return targetList;
	}
	/**
	 * 拷贝工具
	 * @param <T>
	 * @param
	 * @param factory
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <S,T> T[] copyAsArray(Iterator<S> it,ObjectFactory<S,T> factory){
		List<T> targetList =  copyAsList(it, factory);
		return (T[])targetList.toArray();
	}
	/**
	 * 拷贝工具
	 * @param
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] copyAsArray(Iterator<?> it,Class<T> clazz){
		List<T> targetList =  copyAsList(it, clazz); 
		return (T[])targetList.toArray();
	}
	/**
	 * 深度拷贝，暂不实现
	 * @param source
	 * @param target
	 */
	public static void deepCopy(Object source,Object target){
		BeanUtils.copyProperties(source, target);
	}
}

